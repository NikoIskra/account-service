package com.account.test.AccountRoleTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.exception.BadRequestException;
import com.account.exception.NotFoundException;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.RoleEnum;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleValidator;

@ExtendWith(MockitoExtension.class)
public class AccountRoleValidatorTest {
    @Mock
    AccountRoleRepository accountRoleRepository;

    @InjectMocks
    AccountRoleValidator accountRoleValidator;

    AccountRoleRequestModel accountRoleRequestModel = new AccountRoleRequestModel()
            .role(RoleEnum.CLIENT)
            .status(StatusEnum.REVOKED);

    AccountRole validAccountRole = new AccountRole(UUID.fromString("26bea1b4-ea70-4df2-836b-3ee2f5cece13"), "client",
            "active");

    @Test
    void testValidateAccountRoleNoException() {
    when(accountRoleRepository.existsByAccountID(any())).thenReturn(true);
    List<AccountRole> accountRoleList = new ArrayList<>();
    accountRoleList.add(validAccountRole);
    when(accountRoleRepository.findAllByAccountID(any())).thenReturn(accountRoleList);
    assertDoesNotThrow(
        () -> {
            UUID testUUID = accountRoleValidator.validateAccountRoleRequestData(UUID.fromString("26bea1b4-ea70-4df2-836b-3ee2f5cece13"), accountRoleRequestModel);
        }
        );
    }

    @Test
    void testValidateAccountRoleNotFoundException() {
    when(accountRoleRepository.existsByAccountID(any())).thenReturn(false);
    assertThrows(NotFoundException.class,
        () -> {
            UUID testUUID = accountRoleValidator.validateAccountRoleRequestData(UUID.fromString("26bea1b4-ea70-4df2-836b-3ee2f5cece13"), accountRoleRequestModel);
        }
        );
    }

    @Test
    void testValidateAccountRoleBadRequestException() {
    when(accountRoleRepository.existsByAccountID(any())).thenReturn(true);
    List<AccountRole> accountRoleList = new ArrayList<>();
    validAccountRole.setStatus("revoked");
    accountRoleList.add(validAccountRole);
    when(accountRoleRepository.findAllByAccountID(any())).thenReturn(accountRoleList);
    assertThrows(BadRequestException.class,
        () -> {
            UUID testUUID = accountRoleValidator.validateAccountRoleRequestData(UUID.fromString("26bea1b4-ea70-4df2-836b-3ee2f5cece13"), accountRoleRequestModel);
        }
        );
    }

}
