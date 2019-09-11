package com.example.model;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class LoginEntity {
	
	
	@NotBlank
	@Getter
	@Setter
	private String email;
	
	
	@NotBlank
	@Getter
	@Setter
	private String password;
	
	
	@NotBlank
	@Getter
	@Setter
	private String type;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	
	
	

}
