package com.example.picpay.model;

import com.example.picpay.dto.CustomerRequestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Customer {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Column(unique = true, nullable = false)
	private String cpf;

	@Column(unique = true, nullable = false)
	private String email;

	private String password;

	private String type;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	@JsonManagedReference
	private List<Wallet> wallet;

	public Customer(CustomerRequestDTO customer) {
		this.name = customer.name();
		this.cpf = customer.cpf();
		this.email = customer.email();
		this.password = customer.password();
		this.type = customer.type();
		this.wallet = customer.wallet();
	}
}
