package com.account.test.AccountRoleTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.RoleEnum;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.model.custom.Tuple2;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleValidator;
import com.account.service.impl.AccountRoleServiceImpl;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class AccountRoleServiceTest {

    @Mock
    AccountRoleRepository accountRoleRepository;

    @Mock
    AccountRoleValidator accountRoleValidator;
    
    @Mock
    EntityManager entityManager;

    @Spy
    @InjectMocks
    AccountRoleServiceImpl accountRoleServiceImpl;

    static AccountRole accountRole;

    static AccountRoleRequestModel accountRoleRequestModel;

    static AccountRoleReturnModel accountRoleReturnModel;

    static Tuple2<AccountRoleReturnModel, Boolean> tuple2;

    @BeforeEach
    void setup() {
        accountRole = new AccountRole(UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), "client", "active");
        accountRoleRequestModel = new AccountRoleRequestModel(RoleEnum.CLIENT);
        accountRoleRequestModel.setStatus(StatusEnum.ACTIVE);
        accountRoleReturnModel = new AccountRoleReturnModel()
        .ok(true)
        .result(
            new AccountRoleReturnModelResult()
            .id(UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"))
            .role("client")
            .status("active")
            .createdAt(Instant.now().getEpochSecond())
            .updatedAt(Instant.now().getEpochSecond())
        );
        tuple2 = new Tuple2<AccountRoleReturnModel,Boolean>(accountRoleReturnModel, true);
    }

    @Test
    void testInsertAccountRole() {
        doReturn(tuple2).when(accountRoleServiceImpl).save(
            UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), accountRoleRequestModel
            );
        Tuple2 tupleReturn = accountRoleServiceImpl.save(
            UUID.fromString("f90736af-a74c-48c9-a483-4f928135a361"), accountRoleRequestModel
            );
        assertNotNull(tupleReturn);
    }
}
