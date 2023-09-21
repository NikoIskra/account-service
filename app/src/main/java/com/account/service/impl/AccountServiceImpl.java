package com.account.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountService;
import com.account.service.ValidateAccountDataService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepository;

    private final ValidateAccountDataService validateAccountDataService;

    public ReturnModel mapAccountToReturnModel(Account account) {
        Timestamp createdAtTimestamp = accountRepository.getCreatedAtTimestampFromID(account.getId());
        ReturnModelResult returnModelResult = new ReturnModelResult()
                .id(account.getId())
                .email(account.getEmail())
                .username(account.getUsername())
                .status(account.getStatus())
                .createdAt(createdAtTimestamp.getTime());
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
        validateAccountDataService.validateData(requestModel);
        Account account = accountRepository.saveAndFlush(mapRequestModelToAccount(requestModel));
        AccountRole accountRole = new AccountRole(account.getId(), "client", "active");
        accountRoleRepository.saveAndFlush(accountRole);
        return mapAccountToReturnModel(account);
    }
}
