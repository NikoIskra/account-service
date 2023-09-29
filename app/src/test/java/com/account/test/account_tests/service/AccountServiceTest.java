package com.account.test.account_tests.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountValidator;
import com.account.service.impl.AccountServiceImpl;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountValidator accountValidator;

    @Mock
    AccountRoleRepository accountRoleRepository;

    @Mock
    EntityManager entityManager;

    @Spy
    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    static Account account;

    static AccountRole accountRole;

    static ReturnModel returnModel;

    static RequestModel requestModel;

    @BeforeEach
    void setup() {
        account = new Account("tesmail", "testusername", "testpassword", "active");
        account.setId(UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c"));
        account.setCreatedAt(Timestamp.from(Instant.now()));
        accountRole = new AccountRole(UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c"), "client", "active");
        requestModel = new RequestModel()
        .email("testmail")
        .username("testusername")
        .password("testpass");
        returnModel = new ReturnModel()
        .ok(true)
        .result(
            new ReturnModelResult()
            .id(UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c"))
            .email("testmail")
            .username("username")
            .status("active")
            .createdAt(Instant.now().getEpochSecond())
        );
    }

    @Test
    void testInsertAccount() {
        doReturn(returnModel).when(accountServiceImpl).save(requestModel);
        ReturnModel returnModel = accountServiceImpl.save(requestModel);
        assertNotNull(returnModel);
    }
}
