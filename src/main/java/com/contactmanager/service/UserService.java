package com.contactmanager.service;

import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.enitiy.Contact;
import com.contactmanager.enitiy.User;

public interface UserService {
	
	public void save(User user);
	public User findByEmail(String username);
	public void save(Contact contact,MultipartFile file);
	
}
