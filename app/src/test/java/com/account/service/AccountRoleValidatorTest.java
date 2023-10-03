package com.account.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.exception.NotFoundException;
import com.account.model.AccountRoleRequestModel;
import com.account.model.RoleEnum;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleValidator;

@ExtendWith(MockitoExtension.class)
public class AccountRoleValidatorTest {

    @Mock
    AccountRoleRepository accountRoleRepository;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountRoleValidator accountRoleValidator;


    private static AccountRole createAccountRoleActive() {
        AccountRole accountRole = new AccountRole();
        accountRole.setStatus("active");
        return accountRole;
    }

    private static AccountRole createAccountRole() {
        AccountRole accountRole = new AccountRole();
        accountRole.setStatus("revoked");
        return accountRole;
    }

    private static AccountRoleRequestModel createAccountRoleRequestModel() {
        AccountRoleRequestModel accountRoleRequestModel = new AccountRoleRequestModel();
        accountRoleRequestModel.setStatus(StatusEnum.ACTIVE);
        return accountRoleRequestModel;
    
    }

    private static AccountRoleRequestModel createAccountRoleRequestModelStatusRevoked() {
        AccountRoleRequestModel accountRoleRequestModel = new AccountRoleRequestModel();
        accountRoleRequestModel.setStatus(StatusEnum.REVOKED);
        accountRoleRequestModel.setRole(RoleEnum.CLIENT);
        return accountRoleRequestModel;
    }

    private static final UUID uuid = UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361");

    @Test
    void testValidateAccountRoleRequestData() {
        when(accountRepository.existsById(uuid)).thenReturn(true);
        AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
        assertDoesNotThrow(
            () -> accountRoleValidator.validateAccountRoleRequestData(uuid, accountRoleRequestModel)
        );
    }

    @Test
    void testValidateAccountRoleRequestData_accNotFound() {
        when(accountRepository.existsById(uuid)).thenReturn(false);
        AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModelStatusRevoked();
        assertThrows(NotFoundException.class,
        () -> accountRoleValidator.validateAccountRoleRequestData(uuid, accountRoleRequestModel)
        );
    }

    @Test
    void testValidateAccountRoleRequestData_roleNotFound() {
        when(accountRepository.existsById(uuid)).thenReturn(true);
        when(accountRoleRepository.existsByAccountIDAndRole(uuid, RoleEnum.CLIENT.getValue())).thenReturn(false);
        AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModelStatusRevoked();
        assertThrows(NotFoundException.class,
        () -> accountRoleValidator.validateAccountRoleRequestData(uuid, accountRoleRequestModel)
        );
    }

    @Test
    void testValidateAccountRoleGetRequest() {
    when(accountRepository.existsById(uuid)).thenReturn(true);
    when(accountRoleRepository.findByAccountIDAndRole(uuid,RoleEnum.CLIENT.getValue())).thenReturn(Optional.of(createAccountRoleActive()));
    assertDoesNotThrow(
    () -> accountRoleValidator.validateAccountRoleGetRequest(uuid, RoleEnum.CLIENT.getValue())
    );
 }


 @Test
    void testValidateAccountRoleGetRequest_accNotExist() {
    when(accountRepository.existsById(uuid)).thenReturn(false);
    assertThrows(NotFoundException.class,
    () -> accountRoleValidator.validateAccountRoleGetRequest(uuid, RoleEnum.CLIENT.getValue())
    );
    verify(accountRepository).existsById(uuid);
    verifyNoInteractions(accountRoleRepository);
 }

  @Test
    void testValidateAccountRoleGetRequest_accAndIdNotExist() {
    when(accountRepository.existsById(uuid)).thenReturn(true);
    when(accountRoleRepository.findByAccountIDAndRole(uuid,RoleEnum.CLIENT.getValue())).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class,
    () -> accountRoleValidator.validateAccountRoleGetRequest(uuid, RoleEnum.CLIENT.getValue())
    );
 }

 @Test
    void testValidateAccountRoleGetRequest_accWithRoleRevoked() {
    when(accountRepository.existsById(uuid)).thenReturn(true);
    when(accountRoleRepository.findByAccountIDAndRole(uuid,RoleEnum.CLIENT.getValue())).thenReturn(Optional.of(createAccountRole()));
    
    assertThrows(NotFoundException.class,
    () -> accountRoleValidator.validateAccountRoleGetRequest(uuid, RoleEnum.CLIENT.getValue())
    );
 }
}
