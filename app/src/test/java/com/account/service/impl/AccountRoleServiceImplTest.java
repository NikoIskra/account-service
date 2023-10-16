package com.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleIDReturnModelResult;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.model.custom.Tuple2;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.model.RoleEnum;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleValidator;
import com.account.service.EntityConverterService;
import com.account.service.impl.AccountRoleServiceImpl;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class AccountRoleServiceImplTest {

    @Mock
    AccountRoleRepository accountRoleRepository;

    @Mock
    AccountRoleValidator accountRoleValidator;
    
    @Mock
    EntityManager entityManager;

    @Mock
    EntityConverterService entityConverter;

    @InjectMocks
    AccountRoleServiceImpl accountRoleServiceImpl;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");
    
    private static AccountRoleRequestModel createAccountRoleRequestModel() {
        AccountRoleRequestModel accountRoleRequestModel = new AccountRoleRequestModel()
        .role(RoleEnum.CLIENT)
        .status(StatusEnum.ACTIVE);
        return accountRoleRequestModel;
    }

    private static AccountRole createAccountRole() {
        AccountRole accountRole = new AccountRole();
        accountRole.setId(uuid);
        accountRole.setAccountID(uuid);
        accountRole.setRole(RoleEnum.CLIENT.getValue());
        accountRole.setStatus(StatusEnum.ACTIVE.getValue());
        return accountRole;
    }

    private static AccountRole createAccountRoleStatusRevoked() {
        AccountRole accountRole = new AccountRole();
        accountRole.setId(uuid);
        accountRole.setAccountID(uuid);
        accountRole.setRole(RoleEnum.CLIENT.getValue());
        accountRole.setStatus(StatusEnum.REVOKED.getValue());
        return accountRole;
    }

    private static AccountRoleIDReturnModel createAccountRoleIDReturnModel() {
        AccountRoleIDReturnModelResult result = new AccountRoleIDReturnModelResult()
        .roleId(uuid);
        return new AccountRoleIDReturnModel().ok(true).result(result);
    }

    private static AccountRoleReturnModel createAccountRoleReturnModel() {
        AccountRoleReturnModelResult result = new AccountRoleReturnModelResult()
        .id(uuid)
        .role(RoleEnum.CLIENT.getValue())
        .status(StatusEnum.ACTIVE.getValue());
        return new AccountRoleReturnModel().ok(true).result(result);
    }



    @Test
    void testInsertAccountRole_RoleAndStatusSame() {
        AccountRole accountRole = createAccountRole();
        AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
        AccountRoleReturnModel accountRoleReturnModel = createAccountRoleReturnModel();
        List<AccountRole> accountRoles = new ArrayList<>();
        accountRoles.add(accountRole);
        when(entityConverter.convertAccountRoleToReturnModel(accountRole)).thenReturn(accountRoleReturnModel);
        doNothing().when(accountRoleValidator).validateAccountRoleRequestData(uuid, accountRoleRequestModel);
        doNothing().when(entityManager).refresh(any());
        when(accountRoleRepository.findAllByAccountID(uuid)).thenReturn(accountRoles);
        Tuple2<AccountRoleReturnModel,Boolean> tuple2 = accountRoleServiceImpl.save(uuid, accountRoleRequestModel);
        assertEquals(tuple2.getFirst().getResult().getId(), accountRole.getId());
        assertEquals(tuple2.getFirst().getResult().getRole(), accountRole.getRole());
        assertEquals(tuple2.getFirst().getResult().getStatus(), accountRole.getStatus());
        assertEquals(tuple2.getSecond(), true);
    }

    @Test
    void testInsertAccountRole_RoleSame() {
        AccountRole accountRoleUpdate = createAccountRole();
        AccountRole accountRoleStatusRevoked = createAccountRoleStatusRevoked();
        AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
        AccountRoleReturnModel accountRoleReturnModel = createAccountRoleReturnModel();
        List<AccountRole> accountRoles = new ArrayList<>();
        accountRoles.add(accountRoleStatusRevoked);
        when(entityConverter.convertAccountRoleToReturnModel(accountRoleStatusRevoked)).thenReturn(accountRoleReturnModel);
        doNothing().when(accountRoleValidator).validateAccountRoleRequestData(uuid, accountRoleRequestModel);
        doNothing().when(entityManager).refresh(any());
        when(accountRoleRepository.findAllByAccountID(uuid)).thenReturn(accountRoles);
        when(accountRoleRepository.saveAndFlush(any())).thenReturn(accountRoleUpdate);
        Tuple2<AccountRoleReturnModel,Boolean> tuple2 = accountRoleServiceImpl.save(uuid, accountRoleRequestModel);
        assertEquals(tuple2.getFirst().getResult().getId(), accountRoleUpdate.getId());
        assertEquals(tuple2.getFirst().getResult().getRole(), accountRoleUpdate.getRole());
        assertEquals(tuple2.getFirst().getResult().getStatus(), accountRoleUpdate.getStatus());
        assertEquals(tuple2.getSecond(), true);
    }

    @Test
    void testInsertAccountRole() {
        AccountRole accountRoleUpdate = createAccountRole();
        AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
        AccountRoleReturnModel accountRoleReturnModel = createAccountRoleReturnModel();
        List<AccountRole> accountRoles = new ArrayList<>();
        when(entityConverter.convertRequestmodelToAccountRole(uuid, accountRoleRequestModel)).thenReturn(accountRoleUpdate);
        when(entityConverter.convertAccountRoleToReturnModel(accountRoleUpdate)).thenReturn(accountRoleReturnModel);
        doNothing().when(accountRoleValidator).validateAccountRoleRequestData(uuid, accountRoleRequestModel);
        doNothing().when(entityManager).refresh(any());
        when(accountRoleRepository.findAllByAccountID(uuid)).thenReturn(accountRoles);
        when(accountRoleRepository.saveAndFlush(any())).thenReturn(accountRoleUpdate);
        Tuple2<AccountRoleReturnModel,Boolean> tuple2 = accountRoleServiceImpl.save(uuid, accountRoleRequestModel);
        assertEquals(tuple2.getFirst().getResult().getId(), accountRoleUpdate.getId());
        assertEquals(tuple2.getFirst().getResult().getRole(), accountRoleUpdate.getRole());
        assertEquals(tuple2.getFirst().getResult().getStatus(), accountRoleUpdate.getStatus());
        assertEquals(tuple2.getSecond(), false);
    }
    
    @Test
    void testGetAccountRole() {
        doNothing().when(accountRoleValidator).validateAccountRoleGetRequest(uuid, RoleEnum.CLIENT.getValue());
        AccountRole accountRole = createAccountRole();
        AccountRoleIDReturnModel accountRoleIDReturnModel = createAccountRoleIDReturnModel();
        when(entityConverter.convertAccountRoleToIdReturnModel(accountRole)).thenReturn(accountRoleIDReturnModel);
        when(accountRoleRepository.findByAccountIDAndRole(uuid, RoleEnum.CLIENT.getValue())).thenReturn(Optional.of(accountRole));
        AccountRoleIDReturnModel return2 = accountRoleServiceImpl.get(uuid, RoleEnum.CLIENT);
        assertEquals(return2.getResult().getRoleId(), accountRole.getId());
    }
}
