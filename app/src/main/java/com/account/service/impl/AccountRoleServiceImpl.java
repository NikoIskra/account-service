package com.account.service.impl;

import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.model.RoleEnum;
import com.account.model.custom.Tuple2;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleService;
import com.account.service.AccountRoleValidator;
import com.account.service.EntityConverterService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRoleServiceImpl implements AccountRoleService {

  private final AccountRoleRepository accountRoleRepository;

  private final EntityManager entityManager;

  private final AccountRoleValidator accountRoleValidator;

  private final EntityConverterService entityConverter;

  @Override
  @Transactional
  public Tuple2<AccountRoleReturnModel, Boolean> save(
      UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
    Tuple2<AccountRoleReturnModel, Boolean> returnTuple =
        new Tuple2<AccountRoleReturnModel, Boolean>(null, true);
    accountRoleValidator.validateAccountRoleRequestData(accountID, accountRoleRequestModel);
    List<AccountRole> accountRoles = accountRoleRepository.findAllByAccountID(accountID);
    Optional<AccountRole> accountRoleFilterByIDRoleAndStatus =
        accountRoles.stream()
            .filter(
                a ->
                    a.getRole().equals(accountRoleRequestModel.getRole().getValue())
                        && a.getStatus().equals(accountRoleRequestModel.getStatus().getValue()))
            .findFirst();
    Optional<AccountRole> accountRoleFilteredByIDAndRole =
        accountRoles.stream()
            .filter(a -> a.getRole().equals(accountRoleRequestModel.getRole().getValue()))
            .findFirst();
    if (accountRoleFilterByIDRoleAndStatus.isPresent()) {
      AccountRole accountRoleToReturn = accountRoleFilterByIDRoleAndStatus.get();
      entityManager.refresh(accountRoleToReturn);
      AccountRoleReturnModel accountRoleReturnModel =
          entityConverter.convertAccountRoleToReturnModel(accountRoleToReturn);
      returnTuple.setFirst(accountRoleReturnModel);
      return returnTuple;
    } else if (accountRoleFilteredByIDAndRole.isPresent()) {
      AccountRole accountRoleToUpdate = accountRoleFilteredByIDAndRole.get();
      accountRoleToUpdate.setStatus(accountRoleRequestModel.getStatus().getValue());
      accountRoleRepository.saveAndFlush(accountRoleToUpdate);
      AccountRoleReturnModel accountRoleReturnModel =
          entityConverter.convertAccountRoleToReturnModel(accountRoleToUpdate);
      entityManager.refresh(accountRoleToUpdate);
      returnTuple.setFirst(accountRoleReturnModel);
      return returnTuple;
    } else {
      AccountRole accountRole =
          entityConverter.convertRequestmodelToAccountRole(accountID, accountRoleRequestModel);
      accountRoleRepository.saveAndFlush(accountRole);
      entityManager.refresh(accountRole);
      AccountRoleReturnModel accountRoleReturnModel =
          entityConverter.convertAccountRoleToReturnModel(accountRole);
      returnTuple.setFirst(accountRoleReturnModel);
      returnTuple.setSecond(false);
      return returnTuple;
    }
  }

  @Override
  public AccountRoleIDReturnModel get(UUID accountId, RoleEnum role) {
    accountRoleValidator.validateAccountRoleGetRequest(accountId, role.getValue());
    AccountRole accountRole =
        accountRoleRepository.findByAccountIDAndRole(accountId, role.getValue()).get();
    AccountRoleIDReturnModel accountRoleIDReturnModel =
        entityConverter.convertAccountRoleToIdReturnModel(accountRole);
    return accountRoleIDReturnModel;
  }
}
