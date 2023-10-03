package com.account.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private static RequestModel createRequestModel() {
        RequestModel requestModel = new RequestModel();
        requestModel.setEmail("testmail@gmail.com");
        return requestModel;
    }

    @Test
    void testValidateRequestData() {
        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        RequestModel requestModel = createRequestModel();
        assertDoesNotThrow(
            () -> accountValidator.validateRequestData(requestModel)
        );
    }

    @Test
    void testValidateRequestData_conflict() {
        when(accountRepository.existsByEmail(anyString())).thenReturn(true);
        RequestModel requestModel = createRequestModel();
        assertThrows(ConflictException.class,
            () -> accountValidator.validateRequestData(requestModel)
        );
    }
}
