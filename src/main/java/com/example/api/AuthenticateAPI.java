package com.example.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.jwt.JWTFilterUtils;
import com.example.model.AuthToken;
import com.example.model.LoginEntity;
import com.example.repository.CustomerRepo;
import com.example.repository.SellerRepo;
import com.example.service.UserDetailService;


@RestController
@RequestMapping("/api")
public class AuthenticateAPI {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTFilterUtils tokenProvider;
    
    @Autowired
	private SellerRepo sellerRepo;
	
	@Autowired 
	private CustomerRepo customerRepo;
	
	@Autowired
	private UserDetailsService userDetailService;

	@PostMapping("/auth/seller")
	public ResponseEntity<AuthToken> loginSeller(@Valid @RequestBody LoginEntity login) throws Exception {
		authenticate(login.getEmail(), login.getPassword());
		final UserDetails userDetails = userDetailService.loadUserByUsername(login.getEmail());
		final String token = tokenProvider.getToken(userDetails);
        AuthToken uAuthToken = new AuthToken();
        uAuthToken.setToken(token);
        uAuthToken.setType("SELLER");
		return new ResponseEntity<AuthToken>(uAuthToken, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
		throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
		throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
}
