package com.account.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.account.exception.ConflictException;
import com.account.model.RequestModel;
import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;
import com.account.service.ValidateAccountDataService;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ValidateAccountDataServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ValidateAccountDataService validateAccountDataService;


    RequestModel requestModel = new RequestModel()
            .email("testmail2@gmail.com")
            .username("testusername")
            .password("testpassword");

    RequestModel validatedRequestModel = new RequestModel()
            .email("testmail@gmail.com")
            .password("testpassword");

    Account accountWithoutID = new Account("testmail@gmail.com", "testusername", "testpassword", "active");

    @Test
    void testExtractUsernameFromEmail() {
        validateAccountDataService.validateData(validatedRequestModel);
        assertEquals("testmail", validatedRequestModel.getUsername());
    }


    void validateAccountWithExistingUsername() {
        accountRepository.save(accountWithoutID);
        validateAccountDataService.validateData(validatedRequestModel);
    }

    @Test
    @DirtiesContext
    void testValidateAccountWithExistingUsername() {
                Exception thrown = assertThrows(
                ConflictException.class,
                () -> validateAccountWithExistingUsername());
    }

    @Test
    @DirtiesContext
    void testValidationGeneratesRandomSuffix() {
        accountRepository.save(accountWithoutID);
        validateAccountDataService.validateData(requestModel);
        assertNotEquals(accountWithoutID.getUsername(), requestModel.getUsername());
    }
}
