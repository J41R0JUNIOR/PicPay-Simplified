package com.example.picpay.dto;

import com.example.picpay.model.Customer;
import com.example.picpay.model.Wallet;

import java.util.List;

public record CustomerResponseDTO(String name, String cpf, String email, String password, String type, List<Wallet> wallet) {

	public CustomerResponseDTO(Customer customer){
		this(
			customer.getName(),
				customer.getCpf(),
				customer.getEmail(),
				customer.getPassword(),
				customer.getType(),
				customer.getWallet()
		);
	}
}
