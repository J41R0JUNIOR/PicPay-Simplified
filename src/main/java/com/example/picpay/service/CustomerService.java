package com.example.picpay.service;

import com.example.picpay.dto.CustomerRequestDTO;
import com.example.picpay.model.Customer;
import com.example.picpay.model.Wallet;
import com.example.picpay.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer findById(Long id) {
		return customerRepository.findById(id);
	}

	public Customer create(CustomerRequestDTO customer) {
		Customer newCustomer = new Customer(customer);
		linkWallets(customer.wallet(), newCustomer);

		return save(newCustomer);
	}

	private Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	private void linkWallets(List<Wallet> wallets, Customer customer) {
		for (Wallet wallet : wallets) {
			wallet.setCustomer(customer);
		}
	}
}
