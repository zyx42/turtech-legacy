package xyz.zyv42.turtech_legacy.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(){
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(){
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
}
