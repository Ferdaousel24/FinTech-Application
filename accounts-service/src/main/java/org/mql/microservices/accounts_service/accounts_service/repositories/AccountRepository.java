package org.mql.microservices.accounts_service.accounts_service.repositories;

import org.mql.microservices.accounts_service.accounts_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AccountRepository extends JpaRepository<Account, UUID> {
}
