package com.example.picpay.dto;

import com.example.picpay.model.Transaction;

import java.util.Optional;

public record TransactionResponseDTO(Long walletSenderId, Long walletReceiverId, Integer amount, String status) {

	public TransactionResponseDTO(Transaction transaction){
		this(
				transaction.getWalletSenderId(),
				transaction.getWalletReceiverId(),
				transaction.getAmount(),
				transaction.getStatus()
		);
	}

}
