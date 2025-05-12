package com.example.picpay.repository;

import com.example.picpay.model.Transaction;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TransactionRepository extends Repository<Transaction, String> {
	Transaction save(Transaction transaction);
	Transaction findById(String id);
	List<Transaction> findAll();
}
