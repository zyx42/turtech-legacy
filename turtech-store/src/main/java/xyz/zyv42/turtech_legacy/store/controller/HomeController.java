package xyz.zyv42.turtech_legacy.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private final JavaMailSender mailSender;
	
	@Autowired
	public HomeController(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("activeHome", true);
		return "index";
	}

	@GetMapping(value = "/about")
	public String hours(Model model) {
		model.addAttribute("activeAbout", true);
		return "about";
	}

	@GetMapping(value = "/faq")
	public String faq(Model model) {
		model.addAttribute("activeFaq", true);
		return "faq";
	}

	@GetMapping(value = "/contact")
	public String contact(Model model) {
		model.addAttribute("activeContact", true);
		return "contact";
	}
	
	@PostMapping(value = "/sendMessage")
	public String sendMessage(@ModelAttribute("email") String email,
			@ModelAttribute("name") String name,
			@ModelAttribute("message") String message,
			Model model) {

        // sending a feedback message to the websites email
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		emailMessage.setTo("turtechfeedback@gmail.com");
		emailMessage.setFrom(email);
		emailMessage.setText(message);
		emailMessage.setSubject("Contact Us, from " + name);
		
		mailSender.send(emailMessage);
		
		model.addAttribute("activeContact", true);
		model.addAttribute("messageSent", true);
		
		return "contact";
	}
}