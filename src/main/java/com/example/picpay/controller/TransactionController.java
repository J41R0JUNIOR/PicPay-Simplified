package com.example.picpay.controller;

import com.example.picpay.dto.TransactionRequestDTO;
import com.example.picpay.dto.TransactionResponseDTO;
import com.example.picpay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping("")
	public ResponseEntity<TransactionResponseDTO> makeTransaction(@RequestBody TransactionRequestDTO transaction) {
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.transaction(transaction));
	}
}
