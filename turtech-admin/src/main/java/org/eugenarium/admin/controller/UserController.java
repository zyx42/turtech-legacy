package org.eugenarium.admin.controller;

import org.eugenarium.admin.persistence.domain.User;
import org.eugenarium.admin.persistence.domain.security.Role;
import org.eugenarium.admin.persistence.service.RoleService;
import org.eugenarium.admin.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

import java.util.ArrayList;
import java.util.List;

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
    public String addUserPost(@ModelAttribute("user") User user) {

        // setting roles for a user
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findByName(role.getName()));
        }
        user.setRoles(roles);
        // adding user to a database
        userService.save(user);

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
    public String updateUserPost(@ModelAttribute("product") User user) {

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
    public String deleteUser(@PathParam("id") Long id) {

        userService.deleteById(id);

        return "forward:/user/userList";
    }
}
