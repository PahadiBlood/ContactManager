package com.contactmanager.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
			@RequestParam("profile_image") MultipartFile file, Principal principal, 
			@RequestParam("id") int id,
			HttpSession session, @RequestParam("profile_img") String img) {
		if (result.hasErrors()) {
			model.addAttribute("contact", contact);
			return "normal/contact-form";
		}

		try {
			String username = principal.getName();
			User user = service.findByEmail(username);
			contact.setCid(id);
			user.getContacts().add(contact);
			service.save(contact, file,img);
			model.addAttribute("contact", new Contact());
			if (id == 0) {
				session.setAttribute("message", new Message("Contact has been saved.", "success"));
			} else {
				session.setAttribute("message", new Message("Contact has been updated.", "success"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "normal/contact-form";
	}

//show all the contacts

	@RequestMapping("/view-contacts/{page}")
	public String showContacts(Model model, Principal principal, @PathVariable("page") int page) {
		String username = principal.getName();
		User user = service.findByEmail(username);

		Pageable pagable = PageRequest.of(page, 8);

		Page<Contact> contacts = service.findContactsByUserId(user.getId(), pagable);
		model.addAttribute("title", "View Contacts");
		model.addAttribute("contact", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/view-contacts";
	}

	// view contacts profile

	@RequestMapping("/contact/profile/{id}")
	public String viewContactProfile(@PathVariable("id") int id, Model model, Principal principal) {

		try {
			String username = principal.getName();
			User user = service.findByEmail(username);

			Contact contact = service.findById(id);

			if (user.getId() == contact.getUser().getId()) {
				model.addAttribute("contact", contact);
				model.addAttribute("title", user.getName());

			} else {
				model.addAttribute("title", "Contact Profile");

			}
		} catch (Exception e) {
			model.addAttribute("message", new Message("Contact not found.", "danger"));
		}
		return "normal/contact-profile";
	}

	@PostMapping("/delete/{currentPageId}/{id}")
	public String delteContact(@PathVariable("id") int id, @PathVariable("currentPageId") int currentPageId,
			HttpSession session, Principal principal) {
		String username = principal.getName();
		User user = service.findByEmail(username);
		Contact contact = service.findById(id);
		if (user.getId() == contact.getUser().getId()) {
			service.delete(contact);
			session.setAttribute("deleteContact", new Message("Contact has been deleted", "success"));
		} else {
			session.setAttribute("deleteContact", new Message("Something went wrong", "danger"));
		}
		return "redirect:/user/view-contacts/" + currentPageId;
	}

	@PostMapping("/delete/{id}")
	public String delteContactProfile(@PathVariable("id") int id, HttpSession session, Principal principal) {
		String username = principal.getName();
		User user = service.findByEmail(username);
		Contact contact = service.findById(id);
		if (user.getId() == contact.getUser().getId()) {
			service.delete(contact);
		} else {
			session.setAttribute("deleteContact", new Message("Something went wrong", "danger"));
		}
		return "redirect:/user/contact/profile/" + id;
	}

	@PostMapping("/update/{id}")
	public String updateContact(Model model, @PathVariable("id") int id, Principal principal) {
		String username = principal.getName();
		User user = service.findByEmail(username);
		Contact contact = service.findById(id);
		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
			return "normal/contact-form";
		} else {
			return "redirect:/user/veiw-contacts/0";
		}

	}
}