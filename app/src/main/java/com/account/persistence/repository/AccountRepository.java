package com.account.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.persistence.entity.Account;


public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findById(UUID id);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);
}
