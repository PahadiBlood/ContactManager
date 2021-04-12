package com.contactmanager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.enitiy.Contact;
import com.contactmanager.enitiy.OTP;
import com.contactmanager.enitiy.User;

public interface UserService {
	
	public void save(User user);
	public User findByEmail(String username);
	public void save(Contact contact,MultipartFile file,String img);
	public Page<Contact> findContactsByUserId(int userId,Pageable pageable);
	public Contact findById(int id);
	public void delete(Contact contact);
	public List<Contact> findByNameContainingAndUser(String name,User user);
	public String findEmail(String email);
	public void save(OTP otp);
	
}
