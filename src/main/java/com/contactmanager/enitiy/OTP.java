package com.contactmanager.enitiy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="otp")
public class OTP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int otp;
	private String email;
	
	
	
	public OTP(int otp, String email) {
		super();
		this.otp = otp;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
