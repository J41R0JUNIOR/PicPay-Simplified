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
import java.util.Optional;

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
		Optional<Wallet> sender = customerRepository.findWalletById(transaction.walletSenderId());

		if(sender.isEmpty()) throw new IllegalArgumentException("Invalid sender id");

		if(sender.get().getCustomer().getType().equals(CustomerType.SHOPKEEPER.toString())) throw new IllegalArgumentException("Invalid sender type");

		if(sender.get().getBalance() < transaction.amount()) throw new IllegalArgumentException("Insufficient balance");

		try {
			if (!authService.isAuthorized()){ throw new IllegalArgumentException("Unauthorized access"); }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Optional<Wallet> receiver = customerRepository.findWalletById(transaction.walletReceiverId());

		if(receiver.isEmpty()) throw new IllegalArgumentException("Invalid receiver id");

		moveMoney(sender.get(), receiver.get(), transaction.amount());

		Transaction newTransaction = new Transaction();
		newTransaction.setWalletSenderId(sender.get().getId());
		newTransaction.setWalletReceiverId(receiver.get().getId());
		newTransaction.setAmount(transaction.amount());
		newTransaction.setStatus("COMPLETED");

		TransactionResponseDTO transactionResponseDTO = new  TransactionResponseDTO(transactionRepository.save(newTransaction));

		try {
			notificationService.sendNotification();
		} catch (IOException e) {
			throw new RuntimeException("Notification failed");
		}

		return transactionResponseDTO;
	}

	public List<WalletReponseDTO> revertTransaction(String transactionId){
		Transaction transaction = transactionRepository.findById(transactionId);

		if(transaction == null) throw new IllegalArgumentException("Invalid transaction id");

		System.out.println("trancacaodkfalsdfjlasdflkdsjflkasdjflkjdsfkl "+transaction.getStatus());
		if(transaction.getStatus().equals("REVERTED")) throw new IllegalArgumentException("transaction already reverted");

		Optional<Wallet> sender = customerRepository.findWalletById(transaction.getWalletSenderId());
		Optional<Wallet> receiver = customerRepository.findWalletById(transaction.getWalletReceiverId());

		if(sender.isEmpty() || receiver.isEmpty()) throw new IllegalArgumentException("Invalid wallet id");

		transaction.setStatus("REVERTED");
		transactionRepository.save(transaction);

		moveMoney(receiver.get(), sender.get(), transaction.getAmount());

		return List.of(new WalletReponseDTO(sender.get()), new WalletReponseDTO(receiver.get()));
	}

	private void moveMoney(Wallet sender, Wallet receiver, Integer amount){
		sender.setBalance(sender.getBalance() - amount);
		receiver.setBalance(receiver.getBalance() + amount);

		customerRepository.save(sender);
		customerRepository.save(receiver);
	}
}
