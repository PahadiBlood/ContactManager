package com.contactmanager.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contactmanager.enitiy.User;

public interface UserReposatory extends JpaRepository<User, Integer> {
	
	public User findByEmail(String username);
	
	@Query("select email from User where email=?1")
	public String findEmail(String email);

}
