package com.example.picpay.dto;

public record TransactionRequestDTO(Long walletSenderId, Long walletReceiverId, Integer amount, String status) {
}
