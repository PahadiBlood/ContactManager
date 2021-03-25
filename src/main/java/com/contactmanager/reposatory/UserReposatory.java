package com.contactmanager.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactmanager.enitiy.User;

public interface UserReposatory extends JpaRepository<User, Integer> {
	
	public User findByEmail(String username);

}
