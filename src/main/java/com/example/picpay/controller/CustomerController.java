package com.example.picpay.controller;

import com.example.picpay.dto.CustomerRequestDTO;
import com.example.picpay.dto.CustomerResponseDTO;
import com.example.picpay.model.Customer;
import com.example.picpay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@GetMapping("")
	ResponseEntity<List<CustomerResponseDTO>> findAll() {
		return ResponseEntity.ok().body(
				customerService.findAll().stream().map(CustomerResponseDTO::new).toList()
		);
	}

	@GetMapping("/{id}")
	ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(new CustomerResponseDTO(customerService.findById(id)));
	}

	@PostMapping("")
	ResponseEntity<Customer> create(@RequestBody CustomerRequestDTO customer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
	}
}
