package com.account.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.account.exception.ConflictException;
import com.account.model.RequestModel;
import com.account.persistence.repository.AccountRepository;
import com.account.service.AccountValidator;

@ExtendWith(MockitoExtension.class)
public class AccountValidatorTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountValidator accountValidator;

    RequestModel requestModel = new RequestModel("email", "password");

    @Test
    void testValidateAccountNoException() {
        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        assertDoesNotThrow(
            () -> accountValidator.validateRequestData(requestModel)
        );
    }

    @Test
    void testValidateAccountThrowsException() {
        when(accountRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(ConflictException.class,
        () -> accountValidator.validateRequestData(requestModel)
        );
    }
}
