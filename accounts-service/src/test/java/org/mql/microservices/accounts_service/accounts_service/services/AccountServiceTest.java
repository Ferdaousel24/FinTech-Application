package org.mql.microservices.accounts_service.accounts_service.services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mql.microservices.accounts_service.accounts_service.models.Account;
import org.mql.microservices.accounts_service.accounts_service.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock // crée un faux repository
    AccountRepository repository;

    @InjectMocks // injecte le mock du repository dans le service   
    AccountService service;

    @Test // test de la méthode deposit
    void deposit_increasesBalance() {
        // Given : un compte de 100.00 que le faux repository va "trouver"
        UUID id = UUID.randomUUID();
        Account account = new Account("Alice", new BigDecimal("100.00"));
        when(repository.findById(id)).thenReturn(Optional.of(account));

        // When : on dépose 50.00
        Account result = service.deposit(id, new BigDecimal("50.00"));

        // Then : le solde doit être 150.00
        assertThat(result.getBalance()).isEqualByComparingTo("150.00");
    }

    @Test
    void withdraw_decreasesBalance() {
        // Given: an account with 100.00
        UUID id = UUID.randomUUID();
        Account account = new Account("Alice", new BigDecimal("100.00"));
        when(repository.findById(id)).thenReturn(Optional.of(account));

        // When: we withdraw 40.00
        Account result = service.withdraw(id, new BigDecimal("40.00"));

        // Then: the balance is 60.00
        assertThat(result.getBalance()).isEqualByComparingTo("60.00");
}
}