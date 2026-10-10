package org.mql.microservices.accounts_service.accounts_service.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(BigDecimal balance, BigDecimal requestedAmount) {
        super("Insufficient funds: balance=" + balance + ", requested=" + requestedAmount);
    }
}
