package com.example.picpay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

	Long walletSenderId;

	Long walletReceiverId;

	Integer amount;

	String status;
}
