package com.example.picpay.repository;

import com.example.picpay.model.Customer;
import com.example.picpay.model.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {
	Customer findById(Long id);
	List<Customer> findAll();
	Customer save(Customer customer);

	@Query("select w from Wallet w where w.id = ?1")
	Optional<Wallet> findWalletById(Long id);

	@Query("select w from Wallet w")
	Optional<List<Wallet>> findAllWallets();

	Wallet save(Wallet wallet);
}