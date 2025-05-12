package com.example.picpay.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer balance;

	private String title;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonBackReference

	private Customer customer;
}
