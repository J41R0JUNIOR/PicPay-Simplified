package com.example.picpay.dto;

import com.example.picpay.model.Wallet;

public record WalletReponseDTO(Long id, Integer balance, String title) {
	public WalletReponseDTO(Wallet wallet){
		this(
				wallet.getId(),
				wallet.getBalance(),
				wallet.getTitle()
		);
	}
}
