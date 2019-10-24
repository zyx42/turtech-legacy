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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

	@RequestMapping("/forgotPassword")
	public String forgotPassword(@ModelAttribute("email") String email,
			HttpServletRequest request,
			Model model) {

		model.addAttribute("classActiveForgotPassword", true);

		User user = userService.findByEmail(email);

		// response in case of provided email does not exist in the database
		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "/#login";
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

		model.addAttribute("forgotPasswordEmailSent", "true");

		return "/#login";
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

	@RequestMapping(value="/addNewCreditCard", method= RequestMethod.POST)
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

	@RequestMapping(value="/addNewShippingAddress", method= RequestMethod.POST)
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
			return "badRequestPage";
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
			return "badRequestPage";
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

	@RequestMapping(value="/setDefaultPayment", method= RequestMethod.POST)
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
			return "badRequestPage";
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

	@RequestMapping(value="/newUser", method = RequestMethod.POST)
	public String newUserPost(HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			@ModelAttribute("password") String password,
			@ModelAttribute("firstName") String firstName,
			@ModelAttribute("lastName") String lastName,
			@ModelAttribute("phone") String phone,
			Model model) throws Exception {

		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		// response in case the username doesn't exist
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);

			return "forward:/#signup";
		}

		// response in case the email already exists
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);

			return "forward:/#signup";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhone(phone);

		// encrypting and salting the given password
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		// setting the roles for the user
		Role role = roleService.findByName("ROLE_USER");
		List<Role> roleSet = new ArrayList<>();
		roleSet.add(role);
		user.setRoles(roleSet);
		
		// providing a user with a personal shopping cart
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(user);
		user.setShoppingCart(shoppingCart);
		
		userService.createUser(user);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		// sending an email to a new user
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		mailSender.send(email);

		model.addAttribute("emailSent", "true");
		model.addAttribute("orderList", user.getOrderList());

		return "index";
	}


	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {

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

	@PostMapping(value = "/updateUserInfo")
	public String updateUserInfo(@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model) throws Exception {

		User currentUser = userService.findById(user.getId());

		if (currentUser == null) {
			throw new Exception ("User not found");
		}

		// check if email already exists
		if (userService.findByEmail(user.getEmail())!=null) {
			if(!userService.findByEmail(user.getEmail()).getId().equals(currentUser.getId())) {
				model.addAttribute("emailExists", true);
				return "myAccount";
			}
		}

		// check if username already exists
		if (userService.findByUsername(user.getUsername())!=null) {
			if (!userService.findByUsername(user.getUsername()).getId().equals(currentUser.getId())) {
				model.addAttribute("usernameExists", true);
				model.addAttribute("classActiveProfile", true);
				return "myAccount";
			}
		}

		// updating password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				model.addAttribute("classActiveProfile", true);

				return "myAccount";
			}
		}

		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhone(user.getPhone());

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
