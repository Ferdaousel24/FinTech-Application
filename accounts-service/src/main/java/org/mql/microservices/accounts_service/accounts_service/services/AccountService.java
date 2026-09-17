package org.mql.microservices.accounts_service.accounts_service.services;

import org.mql.microservices.accounts_service.accounts_service.models.Account;
import org.mql.microservices.accounts_service.accounts_service.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(String ownerName, BigDecimal openingBalance) {
        Account account = new Account(ownerName, openingBalance);
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true) //
    public Account getAccount(UUID id) {
        return findOrThrow(id); //findOrThrow(id) runs — if the account doesn't exist, it throws an exception. Spring catches that this method threw an exception, and automatically rolls back the transaction (nothing to roll back yet here, but the mechanism is armed from the start).
    }

    @Transactional(readOnly = true)
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account deposit(UUID id, BigDecimal amount) {
        Account account = findOrThrow(id);
        account.setBalance(account.getBalance().add(amount));
        return account;
    }

    @Transactional
    public Account withdraw(UUID id, BigDecimal amount) {
        Account account = findOrThrow(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds: balance=" + account.getBalance() + ", requested=" + amount);
        }
        account.setBalance(account.getBalance().subtract(amount));
        return account;
    }

    private Account findOrThrow(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }
}