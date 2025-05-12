package com.example.picpay.repository;

import com.example.picpay.model.Customer;
import com.example.picpay.model.Wallet;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface CustomerRepository extends Repository<Customer, Long> {
	Customer findById(Long id);
	List<Customer> findAll();
	Customer save(Customer customer);
	Wallet findWalletById(Long id);
}
