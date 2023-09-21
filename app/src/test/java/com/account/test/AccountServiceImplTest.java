package com.account.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;
import com.account.service.impl.AccountServiceImpl;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountServiceImplTest {

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @Autowired
    AccountRepository accountRepository;

    RequestModel emptyRequestModel = new RequestModel();

    RequestModel validRequestModel = new RequestModel()
            .email("testmail@gmail.com")
            .username("testusername")
            .password("testpassword");

    Account accountWithoutID = new Account("testmail@gmail.com", "testusername", "testpassword", "active");

    @Test
    void testMapAccountToReturnModel() {
        Account savedAccount = accountRepository.saveAndFlush(accountWithoutID);
        ReturnModel returnModel = accountServiceImpl.mapAccountToReturnModel(savedAccount);
        assertEquals(returnModel.getResult().getEmail(), savedAccount.getEmail());
        assertEquals(returnModel.getResult().getUsername(), savedAccount.getUsername());
        assertEquals(returnModel.getResult().getStatus(), savedAccount.getStatus());
    }

    void mapAccountWithoutIDToReturnModel() {
        ReturnModel returnModel = accountServiceImpl.mapAccountToReturnModel(accountWithoutID);
        assertEquals(returnModel.getResult().getEmail(), accountWithoutID.getEmail());
        assertEquals(returnModel.getResult().getUsername(), accountWithoutID.getUsername());
        assertEquals(returnModel.getResult().getStatus(), accountWithoutID.getStatus());
    }

    @Test
    void testMapAccountWithoutIDToReturnModel() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> mapAccountWithoutIDToReturnModel());
    }

    @Test
    void testMapRequestModelToAccount() {
        Account account = accountServiceImpl.mapRequestModelToAccount(validRequestModel);
        assertEquals(validRequestModel.getEmail(), account.getEmail());
        assertEquals(validRequestModel.getUsername(), account.getUsername());
        assertEquals(validRequestModel.getPassword(), account.getPassword());
        assertEquals("active", account.getStatus());
    }

    @Test
    void testMapEmptyRequestModelToAccount() {
        Account account = accountServiceImpl.mapRequestModelToAccount(emptyRequestModel);
        assertEquals(null, account.getEmail());
        assertEquals(null, account.getUsername());
        assertEquals(null, account.getPassword());
        assertEquals("active", account.getStatus());
    }
}
