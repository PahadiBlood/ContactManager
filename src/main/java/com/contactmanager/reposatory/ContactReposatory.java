package com.contactmanager.reposatory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contactmanager.enitiy.Contact;

public interface ContactReposatory extends JpaRepository<Contact, Integer> {

	//Pageable has two recored 
	//Current page number
	//Show Contacts per page
	
	
	@Query("from Contact as c where c.user.id=?1")
	public Page<Contact> findContactsByUserId(int userId,Pageable pageable);
	
	public Contact findById(int id);
}
