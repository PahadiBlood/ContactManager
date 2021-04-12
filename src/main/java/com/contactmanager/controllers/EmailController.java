package com.contactmanager.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactmanager.enitiy.User;
import com.contactmanager.helper.SendEmail;
import com.contactmanager.service.UserService;
@Controller
@RequestMapping("/verify")
public class EmailController {

	@Autowired
	private UserService service;

	@Autowired
	private SendEmail sendEmail;
	
	@ModelAttribute
	public void addData(Model model, Principal principal) {
		String username = principal.getName();
		User user = service.findByEmail(username);
		model.addAttribute("user", user);
	}
	// send otp

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("forgot-password") String email, Principal principal, HttpSession session,
			Model model) {

		String checkEmail = service.findEmail(email);
		if (email.equals(checkEmail)) {
			boolean result=sendEmail.sendEmail(email);
			if(result!=false) {
				model.addAttribute("smsg","We have sent you an otp please check your email");
				return "normal/verify-otp";
			}else {
				session.setAttribute("message", "Something went wrong please try again or later.");
				return "normal/settings";
				
			}
			
		} else {
			session.setAttribute("message", "Email not found.");
			return "normal/settings";
		}

	}

	public String verifyOtp() {

		return "change-password";
	}

}
