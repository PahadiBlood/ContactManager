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
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.enitiy.Contact;
import com.contactmanager.enitiy.User;
import com.contactmanager.helper.Message;
import com.contactmanager.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@ModelAttribute
	public void addData(Model model, Principal principal) {
		String username = principal.getName();
		User user = service.findByEmail(username);
		model.addAttribute("user", user);
	}

	@RequestMapping("/dashboard")
	public String dasthboard(Model model, Principal principal) {

		return "normal/user-dashboard";
	}

	@RequestMapping("/contact-form")
	public String showContactform(Model model, User user) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/contact-form";
	}

	@PostMapping("/addContact")
	public String getContactData(Contact contact, Model model,
			@RequestParam("profile_image") MultipartFile file,
			Principal principal, HttpSession session) {

		try {

			String username = principal.getName();
			User user = service.findByEmail(username);
			user.getContacts().add(contact);
			service.save(contact, file);
			model.addAttribute("contact", new Contact());
			session.setAttribute("message", new Message("Contact has been saved.", "success"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "normal/contact-form";
	}

}
