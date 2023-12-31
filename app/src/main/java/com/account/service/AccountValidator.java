package com.account.service;

import com.account.exception.ConflictException;
import com.account.model.RequestModel;
import com.account.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountValidator {

  private final AccountRepository accountRepository;

  public void validateRequestData(RequestModel requestModel) {
    String email = requestModel.getEmail();
    if (accountRepository.existsByEmail(email))
      throw new ConflictException("Email already exists!");
  }
}
