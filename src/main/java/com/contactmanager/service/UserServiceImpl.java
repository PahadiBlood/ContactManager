package com.contactmanager.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.enitiy.Contact;
import com.contactmanager.enitiy.User;
import com.contactmanager.reposatory.ContactReposatory;
import com.contactmanager.reposatory.UserReposatory;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder encoder;

	private UserReposatory userRepo;

	@Autowired
	private ContactReposatory contactRepo;

	@Autowired
	public UserServiceImpl(UserReposatory theUserRepo) {
		userRepo = theUserRepo;
	}

	@Override
	@Transactional
	public User findByEmail(String username) {
		User user = userRepo.findByEmail(username);
		return user;
	}

	@Override
	@Transactional
	public void save(Contact contact, MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				contact.setImage(file.getOriginalFilename());

				File filePath = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(filePath.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		contactRepo.save(contact);

	}

	@Override
	@Transactional
	public void save(User user) {

		try {
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(encoder.encode(user.getPassword()));

			userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
