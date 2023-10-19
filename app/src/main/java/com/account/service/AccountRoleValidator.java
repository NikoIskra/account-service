package com.account.service;

import com.account.exception.NotFoundException;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRoleValidator {

  private final AccountRoleRepository accountRoleRepository;

  private final AccountRepository accountRepository;

  public void validateAccountRoleGetRequest(UUID accountID, String role) {
    if (!accountRepository.existsById(accountID)) {
      throw new NotFoundException("no account with that ID exists!");
    } else {
      Optional<AccountRole> accountRole =
          accountRoleRepository.findByAccountIDAndRole(accountID, role);
      if (accountRole.isEmpty()) {
        throw new NotFoundException("No account with that ID and role exists!");
      } else if (StatusEnum.REVOKED.toString().equals(accountRole.get().getStatus())) {
        throw new NotFoundException("Account with that role has been revoked!");
      }
    }
  }

  public void validateAccountRoleRequestData(
      UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
    Boolean accountExists = accountRepository.existsById(accountID);
    if (!accountExists) {
      throw new NotFoundException("no account with that ID found!");
    }
    if (accountRoleRequestModel.getStatus().equals(StatusEnum.REVOKED)) {
      if (!accountRoleRepository.existsByAccountIDAndRole(
          accountID, accountRoleRequestModel.getRole().getValue())) {
        throw new NotFoundException("no account with given role exists to revoke");
      }
    }
  }
}
