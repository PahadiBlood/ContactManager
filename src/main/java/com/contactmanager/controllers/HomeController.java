package com.contactmanager.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contactmanager.enitiy.User;
import com.contactmanager.helper.Message;
import com.contactmanager.service.UserServiceImpl;

@Controller
public class HomeController {

	@Autowired
	public UserServiceImpl serviceImpl;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About");
		return "about";
	}

	@RequestMapping("/signup")
	public String signupForm(Model model) {
		model.addAttribute("title", "Register");
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/do_register")
	public String register(@Valid User user, BindingResult result, Model model, HttpSession session) {

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "signup";
		}

		try {
			serviceImpl.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Register successfully.", "alert-success"));
			return "signup";
		} catch (Exception e) {
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	@RequestMapping("/login")
	public String loginForm(Model model) {
	
		model.addAttribute("title", "Log In");
		model.addAttribute("user", new User());
		return "normal/login";
	}
}
