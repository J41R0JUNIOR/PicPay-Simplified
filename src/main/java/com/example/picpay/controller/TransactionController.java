package com.example.picpay.controller;

import com.example.picpay.dto.TransactionRequestDTO;
import com.example.picpay.dto.TransactionResponseDTO;
import com.example.picpay.dto.WalletReponseDTO;
import com.example.picpay.model.Wallet;
import com.example.picpay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping("")
	public ResponseEntity<TransactionResponseDTO> makeTransaction(@RequestBody TransactionRequestDTO transaction) {
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.transaction(transaction));
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<WalletReponseDTO>> revertTransaction(@PathVariable String id){
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.revertTransaction(id));
	}
}
