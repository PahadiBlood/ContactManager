package com.contactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contactmanager.enitiy.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// fetch user details from database

		User user = userService.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(
					"Enterd email " + username + " is not not register with our website. Please signup first.");
		}
		return new UserDetailsImpl(user);
	}

}
