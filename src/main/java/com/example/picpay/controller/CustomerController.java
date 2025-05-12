package com.example.picpay.controller;

import com.example.picpay.dto.CustomerRequestDTO;
import com.example.picpay.dto.CustomerResponseDTO;
import com.example.picpay.model.Customer;
import com.example.picpay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@GetMapping("")
	ResponseEntity<List<CustomerResponseDTO>> findAll() {
		return ResponseEntity.ok().body(
				customerService.findAll().orElse(List.of()).stream().map(CustomerResponseDTO::new).toList()
		);
	}

	@PostMapping("")
	ResponseEntity<Customer> create(@RequestBody CustomerRequestDTO customer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
	}
}
