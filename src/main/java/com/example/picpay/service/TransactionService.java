package com.example.picpay.service;

import com.example.picpay.dto.TransactionRequestDTO;
import com.example.picpay.dto.TransactionResponseDTO;
import com.example.picpay.dto.WalletReponseDTO;
import com.example.picpay.model.CustomerType;
import com.example.picpay.model.Transaction;
import com.example.picpay.model.Wallet;
import com.example.picpay.repository.CustomerRepository;
import com.example.picpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AuthService authService;
	@Autowired
	NotificationService notificationService;

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

		if(sender == null) throw new IllegalArgumentException("Invalid sender id");

		if(sender.getCustomer().getType().equals(CustomerType.SHOPKEEPER.toString())) throw new IllegalArgumentException("Invalid sender type");

		if(sender.getBalance() < transaction.amount()) throw new IllegalArgumentException("Insufficient balance");

		try {
			if (!authService.isAuthorized()){ throw new IllegalArgumentException("Unauthorized access"); }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Wallet receiver = customerRepository.findWalletById(transaction.walletReceiverId());

		moveMoney(sender, receiver, transaction.amount());

		Transaction newTransaction = new Transaction();
		newTransaction.setWalletSenderId(sender.getId());
		newTransaction.setWalletReceiverId(receiver.getId());
		newTransaction.setAmount(transaction.amount());
		newTransaction.setStatus("COMPLETED");

		TransactionResponseDTO transactionResponseDTO = new  TransactionResponseDTO(transactionRepository.save(newTransaction));

		try {
			notificationService.sendNotification();
		} catch (IOException e) {
//			throw new RuntimeException(e);
			e.printStackTrace();
		}

		return transactionResponseDTO;
	}

	public List<WalletReponseDTO> revertTransaction(String transactionId){
		Transaction transaction = transactionRepository.findById(transactionId);

		if(transaction == null) throw new IllegalArgumentException("Invalid transaction id");

		Wallet sender = customerRepository.findWalletById(transaction.getWalletSenderId());
		Wallet receiver = customerRepository.findWalletById(transaction.getWalletReceiverId());

		transaction.setStatus("REVERTED");
		transactionRepository.save(transaction);

		moveMoney(receiver, sender, transaction.getAmount());

		return List.of(new WalletReponseDTO(sender), new WalletReponseDTO(receiver));
	}

	private void moveMoney(Wallet sender, Wallet receiver, Integer amount){
		sender.setBalance(sender.getBalance() - amount);
		receiver.setBalance(receiver.getBalance() + amount);

		customerRepository.save(sender);
		customerRepository.save(receiver);
	}
}
