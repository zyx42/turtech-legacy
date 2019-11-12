package org.eugenarium.store.controller;

import org.eugenarium.store.persistence.domain.*;
import org.eugenarium.store.persistence.domain.security.PasswordResetToken;
import org.eugenarium.store.persistence.domain.security.Role;
import org.eugenarium.store.persistence.service.*;
import org.eugenarium.store.persistence.service.impl.UserSecurityService;
import org.eugenarium.store.utility.MailConstructor;
import org.eugenarium.store.utility.SecurityUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
public class AccountController {

	private final JavaMailSender mailSender;

	private final MailConstructor mailConstructor;

	private final UserService userService;

	private final UserSecurityService userSecurityService;

	private final UserPaymentService userPaymentService;

	private final UserShippingService userShippingService;

	private final CartItemService cartItemService;

	private final OrderService orderService;

	private final RoleService roleService;

	@Autowired
	public AccountController(JavaMailSender mailSender, MailConstructor mailConstructor, UserService userService, UserSecurityService userSecurityService, UserPaymentService userPaymentService, UserShippingService userShippingService, CartItemService cartItemService, OrderService orderService, RoleService roleService) {
		this.mailSender = mailSender;
		this.mailConstructor = mailConstructor;
		this.userService = userService;
		this.userSecurityService = userSecurityService;
		this.userPaymentService = userPaymentService;
		this.userShippingService = userShippingService;
		this.cartItemService = cartItemService;
		this.orderService = orderService;
		this.roleService = roleService;
	}

	@GetMapping("/signIn")
	public String signIn(Model model) {
		model.addAttribute("signInActive", true);
		User user = new User();
		model.addAttribute("user", user);

		return "login";
	}

	@GetMapping("/signUp")
	public String signUp(Model model) {
		model.addAttribute("signUpActive", true);
		User user = new User();
		model.addAttribute("user", user);

		return "login";
	}

	@PostMapping(value="/newUserAction")
	public String newUserPost(HttpServletRequest request,
							  @ModelAttribute("user") @Valid User user,
							  BindingResult bindingResult,
							  Model model) throws Exception {

		if (bindingResult.hasErrors()) {
			model.addAttribute("signUpActive", true);

			return "login";
		}

		// response in case the username doesn't exist
		if (userService.findByUsername(user.getUsername()) != null) {
			model.addAttribute("usernameExists", true);
			model.addAttribute("signUpActive", true);

			return "login";
		}

		// response in case the email already exists
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExists", true);
			model.addAttribute("signUpActive", true);

			return "login";
		}

		user.setCreatedDate(LocalDateTime.now());
		user.setCreatedBy("oneself");

		// validating user password
		// checking if password is empty or blank
		if (user.getPassword() == null ||
				user.getPassword().isEmpty() ||
				user.getPassword().trim().length() == 0) {

			model.addAttribute("emptyPassword", true);
			model.addAttribute("signUpActive", true);

			return "login";

			// checking if the password follows the pattern: starts with a letter,
			// followed by letters and numbers, 8 through 32 characters long
		} else if (!Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{8,31}", user.getPassword())) {
			model.addAttribute("incorrectPassword", true);
			model.addAttribute("signUpActive", true);

			return "login";
		} else {
			// encrypting and salting the given password
			String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
			user.setPassword(encryptedPassword);
		}

		// setting the role for the user
		Role role = roleService.findByName("ROLE_USER");
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);

		// providing a user with a personal shopping cart
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(user);
		user.setShoppingCart(shoppingCart);

		userService.createUser(user);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		// sending an email to a new user
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail
				(appUrl, request.getLocale(), token, user, user.getPassword());
		mailSender.send(email);

		model.addAttribute("emailSent", true);
		model.addAttribute("orderList", user.getOrderList());

		return "login";
	}


	@RequestMapping("/newUser")
	public String newUser(@RequestParam("token") String token, Model model) {

		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/400";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("user", user);

		model.addAttribute("classActiveProfile", true);

		return "myAccount";
	}

	@RequestMapping("/forgotPassword")
	public String forgotPassword(@ModelAttribute("email") String email,
			HttpServletRequest request,
			Model model) {

		model.addAttribute("forgotPasswordActive", true);

		User user = userService.findByEmail(email);

		// response in case of provided email does not exist in the database
		if (user == null) {
			model.addAttribute("emailNotExist", true);
			model.addAttribute("forgotPasswordActive", true);
			return "login";
		}

		// generating new recovery password for the user
		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		userService.save(user);

		// generating token for the password recovery procedure
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName()+":" + request.getServerPort() + request.getContextPath();

		// constructing an email to send for the user requested password recovery
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		mailSender.send(newEmail);

		model.addAttribute("forgotPasswordEmailSent", true);

		return "login";
	}

	@RequestMapping("/myAccount")
	public String myAccount(Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("classActiveProfile", true);

		return "myAccount";
	}

	@PostMapping(value = "/updateUserInfo")
	public String updateUserInfo(@ModelAttribute("user") @Valid User user,
								 @ModelAttribute("newPassword") String newPassword,
								 BindingResult bindingResult,
								 Model model) throws Exception {

		// response in case of validation failure
		if (bindingResult.hasErrors()) {
			model.addAttribute("classActiveProfile", true);
			return "myAccount";
		}

		User currentUser = userService.findById(user.getId());

		if (currentUser == null) {
			throw new Exception ("User not found");
		}

		// check if username already exists
		if (userService.findByUsername(user.getUsername())!=null) {
			if (!userService.findByUsername(user.getUsername()).getId().equals(currentUser.getId())) {
				model.addAttribute("usernameExists", true);
				model.addAttribute("classActiveProfile", true);
				return "myAccount";
			}
		}

		// check if email already exists
		if (userService.findByEmail(user.getEmail())!=null) {
			if(!userService.findByEmail(user.getEmail()).getId().equals(currentUser.getId())) {
				model.addAttribute("emailExists", true);
				model.addAttribute("classActiveProfile", true);
				return "myAccount";
			}
		}

		// validating user password
		// checking if the field for updating password was left empty of blank
		if (newPassword != null &&
				!newPassword.isEmpty() &&
				newPassword.trim().length() > 0) {

			// checking if the password follows the pattern: starts with a letter,
			// followed by letters and numbers, 8 through 32 characters long
			if (!Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{8,31}", user.getPassword())) {
				model.addAttribute("incorrectPattern", true);
				model.addAttribute("classActiveProfile", true);

				return "myAccount";
				// checking if new password is not the same as the current one
			} else if (SecurityUtility.passwordEncoder().matches(newPassword, currentUser.getPassword())) {
				model.addAttribute("samePassword", true);
				model.addAttribute("classActiveProfile", true);

				return "myAccount";
				// checking if user can authorize himself with a valid password before applying changes
			} else if (SecurityUtility.passwordEncoder().matches(user.getPassword(), currentUser.getPassword())) {
				model.addAttribute("incorrectPassword", true);
				model.addAttribute("classActiveProfile", true);

				return "myAccount";
			} else {
				// encrypting and salting the given password
				String encryptedPassword = SecurityUtility.passwordEncoder().encode(newPassword);
				currentUser.setPassword(encryptedPassword);
			}
		}

		// updating user data
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhone(user.getPhone());
		currentUser.setLastModifiedDate(LocalDateTime.now());
		currentUser.setLastModifiedBy("oneself");

		userService.save(currentUser);

		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveProfile", true);

		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("listOfCreditCards", true);

		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		return "myAccount";
	}

	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		return "myAccount";
	}

	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();


		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);

		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@PostMapping(value="/addNewCreditCard")
	public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		userService.updateUserBilling(userBilling, userPayment, user);

		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@PostMapping(value="/addNewShippingAddress")
	public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		userService.updateUserShipping(userShipping, user);

		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}


	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(@ModelAttribute("id") Long creditCardId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);

		if(!user.getId().equals(userPayment.getUser().getId())) {
			return "400";
		} else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);

			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myAccount";
		}
	}

	@RequestMapping("/updateUserShipping")
	public String updateUserShipping(@ModelAttribute("id") Long shippingAddressId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(shippingAddressId);

		if(!user.getId().equals(userShipping.getUser().getId())) {
			return "400";
		} else {
			model.addAttribute("user", user);

			model.addAttribute("userShipping", userShipping);

			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfCreditCards", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myAccount";
		}
	}

	@PostMapping(value="/setDefaultPayment")
	public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(defaultPaymentId, user);

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@RequestMapping(value="/setDefaultShippingAddress", method= RequestMethod.POST)
	public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long defaultShippingId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultShipping(defaultShippingId, user);

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@RequestMapping("/removeCreditCard")
	public String removeCreditCard(@ModelAttribute("id") Long creditCardId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);

		if(!user.getId().equals(userPayment.getUser().getId())) {
			return "400";
		} else {
			model.addAttribute("user", user);
			userPaymentService.deleteById(creditCardId);

			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myAccount";
		}
	}

	@RequestMapping("/removeUserShipping")
	public String removeUserShipping(@ModelAttribute("id") Long userShippingId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);

		if(!user.getId().equals(userShipping.getUser().getId())) {
			return "400";
		} else {
			model.addAttribute("user", user);

			userShippingService.deleteById(userShippingId);

			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveShipping", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myAccount";
		}
	}

	@RequestMapping("/orderDetail")
	public String orderDetail(@RequestParam("id") Long orderId,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		Order order = orderService.findById(orderId);

		if (!order.getUser().getId().equals(user.getId())) {
			return "400";
		} else {
			List<CartItem> cartItemList = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			UserShipping userShipping = new UserShipping();
			model.addAttribute("userShipping", userShipping);

			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);

			return "myAccount";
		}
	}
}
