package com.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountValidator;
import com.account.service.EntityConverterService;
import jakarta.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  @Mock AccountRepository accountRepository;

  @Mock AccountValidator accountValidator;

  @Mock AccountRoleRepository accountRoleRepository;

  @Mock EntityManager entityManager;

  @Mock EntityConverterService entityConverterService;

  @InjectMocks AccountServiceImpl accountServiceImpl;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static Account createAccount() {
    Account account = new Account();
    account.setId(uuid);
    account.setEmail("testmail123@gmail.com");
    account.setUsername("testusername");
    account.setStatus("active");
    account.setCreatedAt(Timestamp.from(Instant.now()));
    return account;
  }

  private static Account createAccountNoUsername() {
    Account account = new Account();
    account.setId(uuid);
    account.setEmail("testmail123@gmail.com");
    account.setStatus("active");
    account.setCreatedAt(Timestamp.from(Instant.now()));
    return account;
  }

  private static RequestModel createRequestModel() {
    RequestModel requestModel =
        new RequestModel()
            .email("testmail123@gmail.com")
            .username("testusername")
            .password("testpassword");
    return requestModel;
  }

  private static ReturnModel createReturnModel() {
    ReturnModelResult result =
        new ReturnModelResult().email("testmail123@gmail.com").username("testusername").id(uuid);
    return new ReturnModel().ok(true).result(result);
  }

  @Test
  void testInsertAccount() {
    RequestModel requestModel = createRequestModel();
    Account account = createAccount();
    ReturnModel returnModel = createReturnModel();
    doNothing().when(accountValidator).validateRequestData(requestModel);
    doNothing().when(entityManager).refresh(account);
    when(entityConverterService.convertRequestModelToAccount(requestModel)).thenReturn(account);
    when(entityConverterService.convertAccountToReturnModel(account)).thenReturn(returnModel);
    when(accountRepository.existsByUsername(anyString())).thenReturn(false);
    when(accountRepository.saveAndFlush(any())).thenReturn(account);
    when(accountRoleRepository.saveAndFlush(any())).thenReturn(null);
    accountServiceImpl.save(requestModel);
    assertEquals(requestModel.getUsername(), returnModel.getResult().getUsername());
    assertEquals(requestModel.getEmail(), returnModel.getResult().getEmail());
  }
}
