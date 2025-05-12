package com.example.picpay.repository;

import com.example.picpay.model.Customer;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {
	Optional<Customer> findById(Long id);
	Optional<List<Customer>> findAll();
	Customer save(Customer customer);
}
