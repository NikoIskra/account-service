package com.account.persistence.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.account.persistence.entity.AccountRole;


public interface AccountRoleRepository extends JpaRepository<AccountRole, UUID> {
    Optional<AccountRole> findById(UUID id);
    Optional<AccountRole> findByAccountID(UUID accountID);
    List<AccountRole> findAllByAccountID(UUID accountID);
    Boolean existsByAccountID(UUID accountID);
    Boolean existsByAccountIDAndRole (UUID accountID, String role);

    @Query(nativeQuery = true, value = "SELECT created_at FROM account_role WHERE id=?1")
    Timestamp getCreatedAtFromID (UUID accountID);
}
