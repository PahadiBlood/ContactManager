package com.contactmanager.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactmanager.enitiy.OTP;

public interface OtpReposatory extends JpaRepository<OTP, Integer> {

}
