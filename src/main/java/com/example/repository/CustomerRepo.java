package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {

	public Customer findOneByEmail(String email);
}
