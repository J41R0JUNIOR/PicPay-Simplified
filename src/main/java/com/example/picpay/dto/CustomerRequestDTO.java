package com.example.picpay.dto;

import com.example.picpay.model.Wallet;

import java.util.List;

public record CustomerRequestDTO(String name, String cpf, String email, String password, String type, List<Wallet> wallet) {
}
