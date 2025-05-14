package com.example.picpay.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.picpay.model.Wallet;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {
    
    @Autowired
    private CustomerRepository repository;

    @Test
    public void findAllWallets() {
        Optional<List<Wallet>> wallets = repository.findAllWallets();

        assertThat(wallets.isPresent());
    }
}
