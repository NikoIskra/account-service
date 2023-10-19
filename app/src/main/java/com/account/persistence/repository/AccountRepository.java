package com.account.persistence.repository;

import com.account.persistence.entity.Account;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  Optional<Account> findByUsername(String username);

  Optional<Account> findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query(nativeQuery = true, value = "SELECT created_at FROM account WHERE id = ?1")
  Timestamp getCreatedAtTimestampFromID(UUID id);
}
