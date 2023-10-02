package com.account.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.RoleEnum;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleValidator;

@ExtendWith(MockitoExtension.class)
public class AccountRoleValidatorTest {
    @Mock
    AccountRoleRepository accountRoleRepository;

    @Spy
    @InjectMocks
    AccountRoleValidator accountRoleValidator;

    AccountRoleRequestModel accountRoleRequestModel = new AccountRoleRequestModel()
            .role(RoleEnum.CLIENT)
            .status(StatusEnum.REVOKED);

    @Test
    void testValidateAccountRoleRequestData() {
        doNothing().when(accountRoleValidator).validateAccountRoleRequestData(UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), accountRoleRequestModel);
        assertDoesNotThrow(
            () -> accountRoleValidator.validateAccountRoleRequestData(UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), accountRoleRequestModel) 
        );
    }

    @Test
    void testValidateAccountRoleGetRequest() {
        doNothing().when(accountRoleValidator).validateAccountRoleGetRequest(UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), "client");
        assertDoesNotThrow(
            () -> accountRoleValidator.validateAccountRoleGetRequest(UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), "client")
        );
    }
}
