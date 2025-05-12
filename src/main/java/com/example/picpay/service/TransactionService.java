package com.example.picpay.service;

import com.example.picpay.dto.TransactionRequestDTO;
import com.example.picpay.dto.TransactionResponseDTO;
import com.example.picpay.model.Transaction;
import com.example.picpay.model.Wallet;
import com.example.picpay.repository.CustomerRepository;
import com.example.picpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	CustomerRepository customerRepository;

	public TransactionResponseDTO save(Transaction transaction) {
		return new TransactionResponseDTO(transaction);
	}

	public TransactionResponseDTO findById(String id) {
		return new TransactionResponseDTO(transactionRepository.findById(id));
	}

	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	public TransactionResponseDTO transaction(TransactionRequestDTO transaction){
		Wallet sender = customerRepository.findWalletById(transaction.walletSenderId());

		if(sender == null) return null;

		if(sender.getBalance() < transaction.amount()) return null;

		Wallet receiver = customerRepository.findWalletById(transaction.walletReceiverId());

		sender.setBalance(sender.getBalance() - transaction.amount());
		receiver.setBalance(receiver.getBalance() + transaction.amount());

		Transaction newTransaction = new Transaction();
		newTransaction.setWalletSenderId(sender.getId());
		newTransaction.setWalletReceiverId(receiver.getId());
		newTransaction.setAmount(transaction.amount());
		newTransaction.setStatus("COMPLETED");

		return new TransactionResponseDTO(transactionRepository.save(newTransaction));
	}
}
