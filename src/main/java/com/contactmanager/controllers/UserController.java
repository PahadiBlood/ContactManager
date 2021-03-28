package com.contactmanager.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

//show dashboard

	@RequestMapping("/dashboard")
	public String dasthboard(Model model, Principal principal) {

		return "normal/user-dashboard";
	}

//show contact form

	@RequestMapping("/contact-form")
	public String showContactform(Model model, User user) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/contact-form";
	}

//Add contact data in database

	@PostMapping("/addContact")
	public String saveContactData(@Valid Contact contact, BindingResult result, Model model,
			@RequestParam("profile_image") MultipartFile file, Principal principal, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("contact", contact);
			return "normal/contact-form";
		}

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

//show all the contacts

	@RequestMapping("/view-contacts")
	public String showContacts(Model model, Principal principal) {
		String username = principal.getName();
		User user = service.findByEmail(username);

		List<Contact> contacts = user.getContacts();
		model.addAttribute("title","View Contacts");
		model.addAttribute("contact",contacts);
		return "normal/view-contacts";
	}

}
