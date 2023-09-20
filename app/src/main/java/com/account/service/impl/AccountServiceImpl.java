package com.account.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.exception.ConflictException;
import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final AccountRoleRepository accountRoleRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
    }

    public String addRandomSuffixToUsername(String username) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return username + "_" + sb;
    }

    public void validateData(RequestModel requestModel) {
        String email = requestModel.getEmail();
        String username = requestModel.getUsername();
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        if (accountOptional.isPresent())
            throw new ConflictException("Email already exists!");
        if (username == null) {
            requestModel.setUsername(email.substring(0, email.indexOf('@')));
            Optional<Account> accountUsernameOptional = accountRepository.findByUsername(requestModel.getUsername());
            if (accountUsernameOptional.isPresent()) {
                requestModel.setUsername(addRandomSuffixToUsername(requestModel.getUsername()));
            }
        } else {
            Optional<Account> accountUsernameOptional = accountRepository.findByUsername(requestModel.getUsername());
            if (accountUsernameOptional.isPresent()) {
                requestModel.setUsername(addRandomSuffixToUsername(requestModel.getUsername()));
            }
        }
    }

    public ReturnModel mapAccountToReturnModel(Account account) {
        Long longTimeNow = Instant.now().toEpochMilli();
        ReturnModelResult returnModelResult = new ReturnModelResult()
                .id(account.getId())
                .email(account.getEmail())
                .username(account.getUsername())
                .status(account.getStatus())
                .createdAt(longTimeNow);
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
        validateData(requestModel);
        Account account = accountRepository.save(mapRequestModelToAccount(requestModel));
        Account fetchedAccount = accountRepository.findByUsername(account.getUsername()).get();
        AccountRole accountRole = new AccountRole(fetchedAccount.getId(), "client", "active");
        accountRoleRepository.save(accountRole);
        return mapAccountToReturnModel(fetchedAccount);
    }
}
