package com.contactmanager.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactmanager.enitiy.Contact;

public interface ContactReposatory extends JpaRepository<Contact, Integer> {

}
