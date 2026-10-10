package org.mql.microservices.accounts_service.accounts_service.exceptions;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
   public AccountNotFoundException(UUID id) {
        super("Account not found: " + id);
    }


}
