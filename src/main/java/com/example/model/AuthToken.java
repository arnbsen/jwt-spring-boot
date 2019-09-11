package com.example.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class AuthToken implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String token;
	
	@Getter
	@Setter
	private String type;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
