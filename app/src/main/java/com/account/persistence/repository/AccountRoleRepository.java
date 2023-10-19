package com.account.persistence.repository;

import com.account.persistence.entity.AccountRole;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRole, UUID> {
  Optional<AccountRole> findById(UUID id);

  Optional<AccountRole> findByAccountID(UUID accountID);

  List<AccountRole> findAllByAccountID(UUID accountID);

  Boolean existsByAccountID(UUID accountID);

  Boolean existsByAccountIDAndRole(UUID accountID, String role);

  Boolean existsByAccountIDAndStatus(UUID accountID, String status);

  Boolean existsByAccountIDAndRoleAndStatus(UUID accountID, String role, String status);

  Optional<AccountRole> findByAccountIDAndRoleAndStatus(UUID accountID, String role, String status);

  Optional<AccountRole> findByAccountIDAndRole(UUID accountID, String role);

  Optional<AccountRole> findByAccountIDAndStatus(UUID accountID, String status);
}
