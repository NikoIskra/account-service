package com.account.persistence.repository;

import com.account.persistence.entity.Account;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findById(UUID id);
}
