package com.account.service.impl;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountService;
import com.account.service.AccountValidator;
import com.account.service.EntityConverterService;
import com.account.service.RandomTextGenerator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  private final AccountRoleRepository accountRoleRepository;

  private final AccountValidator accountValidator;

  private final EntityManager entityManager;

  private final EntityConverterService entityConverter;

  @Override
  @Transactional
  public ReturnModel save(RequestModel requestModel) {
    accountValidator.validateRequestData(requestModel);
    if (requestModel.getUsername() == null) {
      requestModel.setUsername(
          requestModel.getEmail().substring(0, requestModel.getEmail().indexOf('@')));
    }
    if (accountRepository.existsByUsername(requestModel.getUsername())) {
      requestModel.setUsername(
          RandomTextGenerator.addRandomSuffixToUsername(requestModel.getUsername()));
    }
    Account account = entityConverter.convertRequestModelToAccount(requestModel);
    account.setStatus("active");
    accountRepository.saveAndFlush(account);
    AccountRole accountRole = new AccountRole(account.getId(), "client", "active");
    accountRoleRepository.saveAndFlush(accountRole);
    entityManager.refresh(account);
    return entityConverter.convertAccountToReturnModel(account);
  }
}
