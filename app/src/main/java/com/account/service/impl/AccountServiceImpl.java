package com.account.service.impl;

import org.springframework.stereotype.Service;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountService;
import com.account.service.AccountValidator;
import com.account.service.RandomTextGenerator;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepository;

    private final AccountValidator accountValidator;

    private final EntityManager entityManager;

    public ReturnModel mapAccountToReturnModel(Account account) {
        ReturnModelResult returnModelResult = new ReturnModelResult()
                .id(account.getId())
                .email(account.getEmail())
                .username(account.getUsername())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt().getTime());
        return new ReturnModel().ok(true).result(returnModelResult);
    }

    public Account mapRequestModelToAccount(RequestModel requestModel) {
        Account account = new Account(requestModel.getEmail(), requestModel.getUsername(), requestModel.getPassword(),
                "active");
        return account;
    }

    @Override
    @Transactional
    public ReturnModel save(RequestModel requestModel) {
        accountValidator.validateRequestData(requestModel);
        if (requestModel.getUsername() == null) {
            requestModel.setUsername(requestModel.getEmail().substring(0, requestModel.getEmail().indexOf('@')));
        }
        if (accountRepository.existsByUsername(requestModel.getUsername())) {
            requestModel.setUsername(RandomTextGenerator.addRandomSuffixToUsername(requestModel.getUsername()));
        }
        Account account = accountRepository.saveAndFlush(mapRequestModelToAccount(requestModel));
        AccountRole accountRole = new AccountRole(account.getId(), "client", "active");
        accountRoleRepository.saveAndFlush(accountRole);
        entityManager.refresh(account);
        return mapAccountToReturnModel(account);
    }
}
