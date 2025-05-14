package com.example.picpay.repository;

import java.util.List;
import java.util.Optional;

import com.example.picpay.model.Customer;
import com.example.picpay.model.CustomerType;
import com.example.picpay.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private Customer createSampleCustomer() {
        return new Customer(null, "customerExample", "11111111111", "customer@example.com", "example", CustomerType.COMMON_USER.toString(), null);
    }

    private Wallet createSampleWallet(Customer customer) {
        return new Wallet(null, 100, "bankX", customer);
    }

    @Test
    @DisplayName("Should save and find customer by ID")
    public void findCustomerByIdSuccess() {
        Customer customer = createSampleCustomer();
        Customer saved = repository.save(customer);

        Customer found = repository.findById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    @DisplayName("Should return all customers")
    public void findAllCustomersSuccess() {
        repository.save(createSampleCustomer());
        repository.save(new Customer(null, "second", "22222222222", "second@example.com", "example2", CustomerType.MERCHANT.toString(), null));

        List<Customer> customers = repository.findAll();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return all wallets")
    public void findAllWalletsSuccess() {
        Customer customer = createSampleCustomer();
        Wallet wallet = createSampleWallet(customer);
        customer.setWallet(List.of(wallet));
        repository.save(customer);

        Optional<List<Wallet>> result = repository.findAllWallets();
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return wallet by ID")
    public void findWalletByIdSuccess() {
        Customer customer = createSampleCustomer();
        Wallet wallet = createSampleWallet(customer);
        customer.setWallet(List.of(wallet));
        repository.save(customer);

        Wallet savedWallet = repository.findAllWallets().get().get(0);
        Optional<Wallet> result = repository.findWalletById(savedWallet.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTitle()).isEqualTo("bankX");
    }

    @Test
    @DisplayName("Should return empty when wallet not found")
    public void findWalletByIdFail() {
        Customer customer = createSampleCustomer();
        repository.save(customer);

        Optional<Wallet> result = repository.findWalletById(1L);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should save wallet directly")
    public void saveWalletDirectly() {
        Customer customer = repository.save(createSampleCustomer());
        Wallet wallet = new Wallet(null, 200, "bankY", customer);

        Wallet savedWallet = repository.save(wallet);
        assertThat(savedWallet.getId()).isNotNull();
        assertThat(savedWallet.getBalance()).isEqualTo(200);
    }
}
