package org.mql.microservices.accounts_service.accounts_service.controllers;

import org.mql.microservices.accounts_service.accounts_service.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts") //this is the base path for all endpoints in this controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(request.ownerName(), request.openingBalance()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @GetMapping
    public ResponseEntity<?> listAccounts() {
        return ResponseEntity.ok(accountService.listAccounts());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable UUID id, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.deposit(id, request.amount()));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable UUID id, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.withdraw(id, request.amount()));
    }

    // Small request-body shapes, specific to this controller's API contract
    public record CreateAccountRequest(String ownerName, BigDecimal openingBalance) {}
    public record TransactionRequest(BigDecimal amount) {}
}