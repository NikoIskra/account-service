package com.account.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.account.exception.ConflictException;
import com.account.model.RequestModel;
import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateAccountDataService {

    private final AccountRepository accountRepository;

    private final AddRandomSuffixToUsernameService addRandomSuffixToUsernameService;

        public void validateData(RequestModel requestModel) {
        String email = requestModel.getEmail();
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        if (accountOptional.isPresent())
            throw new ConflictException("Email already exists!");
        if (requestModel.getUsername() == null) {
            requestModel.setUsername(email.substring(0, email.indexOf('@')));
        }
        if (accountRepository.existsByUsername(requestModel.getUsername())) {
            requestModel.setUsername(addRandomSuffixToUsernameService.addRandomSuffixToUsername(requestModel.getUsername()));
        }
    }
}
