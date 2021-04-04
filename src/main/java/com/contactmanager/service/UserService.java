package com.contactmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.enitiy.Contact;
import com.contactmanager.enitiy.User;

public interface UserService {
	
	public void save(User user);
	public User findByEmail(String username);
	public void save(Contact contact,MultipartFile file);
	public Page<Contact> findContactsByUserId(int userId,Pageable pageable);
	public Contact findById(int id);
	public void delete(Contact contact);
	
}
