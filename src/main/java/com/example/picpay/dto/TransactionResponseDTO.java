package com.example.picpay.dto;

import com.example.picpay.model.Transaction;

import java.util.Optional;

public record TransactionResponseDTO(String id, Long walletSenderId, Long walletReceiverId, Integer amount, String status) {

	public TransactionResponseDTO(Transaction transaction){
		this(
				transaction.getId(),
				transaction.getWalletSenderId(),
				transaction.getWalletReceiverId(),
				transaction.getAmount(),
				transaction.getStatus()
		);
	}

}
