package xyz.zyv42.turtech_legacy.admin.controller;

import xyz.zyv42.turtech_legacy.admin.persistence.domain.ShoppingCart;
import xyz.zyv42.turtech_legacy.admin.persistence.domain.User;
import xyz.zyv42.turtech_legacy.admin.persistence.domain.security.Role;
import xyz.zyv42.turtech_legacy.admin.persistence.service.RoleService;
import xyz.zyv42.turtech_legacy.admin.persistence.service.UserService;
import xyz.zyv42.turtech_legacy.admin.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(Model model) {

        // returning a form for adding a user to a database
        User user = new User();
        model.addAttribute("user", user);

        return "addUser";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") @Valid User user,
        BindingResult bindingResult,
        Model model) throws Exception {

        // response in case of validation failure
        if (bindingResult.hasErrors()) {
            return "addUser";
        }

        // response in case the username doesn't exist
		if (userService.findByUsername(user.getUsername()) != null) {
			model.addAttribute("usernameExists", true);

			return "addUser";
		}

		// response in case the email already exists
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExists", true);

			return "addUser";
        }
        
        // validating user password
        // checking if password is empty or blank
        if (user.getPassword() == null ||
            user.getPassword().isEmpty() ||
            user.getPassword().trim().length() == 0) {

            model.addAttribute("emptyPassword", true);

            return "addUser";
            // checking if the password follows the pattern: starts with a letter,
            // followed by letters and numbers, 8 through 32 characters long
        } else if (!Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{8,31}", user.getPassword())) {
            model.addAttribute("incorrectPassword", true);

            return "addUser";
        } else {
            // encrypting and salting the fiven password
            String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);
        }
        
        // setting roles for the user
        List<Role> roles = new ArrayList<>();
		for (Role role : user.getRoles()) {
			roles.add(roleService.findByName(role.getName()));
		}
        user.setRoles(roles);
        
        // providing a user with a personal shopping cart
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);
        
        userService.createUser(user);

        return "redirect:userList";
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("id") Long id, Model model) {

        // returning a user from a database
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "userInfo";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("id") Long id, Model model) {

        // returning a form to update a user
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "updateUser";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("user") @Valid User user,
                                BindingResult bindingResult,
                                Model model) throws Exception {

        // response in case of validation failure
		if (bindingResult.hasErrors()) {
			return "updateUser";
		}

        User currentUser = userService.findById(user.getId());

		if (currentUser == null) {
			throw new Exception ("User not found");
        }

        // check if username already exists
		if (userService.findByUsername(user.getUsername())!=null) {
			if (!userService.findByUsername(user.getUsername()).getId().equals(currentUser.getId())) {
				model.addAttribute("usernameExists", true);
				return "updateUser";
			}
        }

        // check if email already exists
		if (userService.findByEmail(user.getEmail())!=null) {
			if(!userService.findByEmail(user.getEmail()).getId().equals(currentUser.getId())) {
				model.addAttribute("emailExists", true);
				return "updateUser";
			}
		}

        // validating user password
        // checking if the field for updating password was left empty of blank
        if (user.getPassword() != null &&
            !user.getPassword().isEmpty() &&
            user.getPassword().trim().length() > 0) {

            // checking if the password follows the pattern: starts with a letter,
            // followed by letters and numbers, 8 through 32 characters long
            if (!Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{8,31}", user.getPassword())) {
                model.addAttribute("incorrectPassword", true);

               return "updateUser";
            // checking if the new password is not the same as the current one
            } else if (SecurityUtility.passwordEncoder().matches(user.getPassword(), currentUser.getPassword())) {
                model.addAttribute("samePassword", true);

                return "updateUser";
            } else {
                // encrypting and salting the fiven password
                String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
                currentUser.setPassword(encryptedPassword);
            }
        }

        // setting roles for the user
        List<Role> roles = new ArrayList<>();
		for (Role role : user.getRoles()) {
			roles.add(roleService.findByName(role.getName()));
		}
        user.setRoles(roles);

        // updating user data
        currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhone(user.getPhone());

        // updating a user in a database
        userService.save(user);

        return "redirect:/user/userInfo?id="+ user.getId();
    }

    @RequestMapping("/userList")
    public String userList(Model model) {

        //returning a list of all user from a database
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);

        return "userList";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@PathParam("id") Long id) throws Exception {

        User user = userService.findById(id);
        if (user == null) {
            throw new Exception ("User not found");
        } else {
            userService.delete(user);
        }

        return "forward:/user/userList";
    }
}
