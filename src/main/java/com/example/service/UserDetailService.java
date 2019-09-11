package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.model.Seller;
import com.example.repository.CustomerRepo;
import com.example.repository.SellerRepo;


@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private SellerRepo sellerRepo;
	
	@Autowired 
	private CustomerRepo customerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Seller seller = sellerRepo.findOneByEmail(username);
		Customer customer = customerRepo.findOneByEmail(username);
		if (seller == null && customer == null) {
			throw new UsernameNotFoundException("No Seller or Customer Found");
		}
		if (seller == null) {
			return customer;
		} else {
			return seller;
		}
	}

}
