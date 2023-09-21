package com.account.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.persistence.entity.AccountRole;


public interface AccountRoleRepository extends JpaRepository<AccountRole, UUID> {
    Optional<AccountRole> findById(UUID id);
    Optional<AccountRole> findByAccountID(UUID accountID);
    Boolean existsByAccountID(UUID accountID);
}
