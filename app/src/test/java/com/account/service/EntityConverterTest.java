package com.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleIDReturnModelResult;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.model.RoleEnum;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class EntityConverterTest {
  @Mock ModelMapper modelMapper;

  @InjectMocks EntityConverterService entityConverterService;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static Account createAccount() {
    Account account = new Account();
    account.setId(uuid);
    account.setEmail("testmail123@gmail.com");
    account.setUsername("testusername");
    account.setPassword("testpassword");
    account.setStatus("active");
    account.setCreatedAt(Timestamp.from(Instant.now()));
    return account;
  }

  private static Account createAccountNoUsername() {
    Account account = new Account();
    account.setId(uuid);
    account.setEmail("testmail123@gmail.com");
    account.setStatus("active");
    account.setCreatedAt(Timestamp.from(Instant.now()));
    return account;
  }

  private static RequestModel createRequestModel() {
    RequestModel requestModel =
        new RequestModel()
            .email("testmail123@gmail.com")
            .username("testusername")
            .password("testpassword");
    return requestModel;
  }

  private static ReturnModel createReturnModel() {
    ReturnModelResult result =
        new ReturnModelResult().email("testmail123@gmail.com").username("testusername").id(uuid);
    return new ReturnModel().ok(true).result(result);
  }

  private static AccountRoleRequestModel createAccountRoleRequestModel() {
    AccountRoleRequestModel accountRoleRequestModel =
        new AccountRoleRequestModel().role(RoleEnum.CLIENT).status(StatusEnum.ACTIVE);
    return accountRoleRequestModel;
  }

  private static AccountRole createAccountRole() {
    AccountRole accountRole = new AccountRole();
    accountRole.setId(uuid);
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
    AccountRoleIDReturnModelResult result = new AccountRoleIDReturnModelResult().roleId(uuid);
    return new AccountRoleIDReturnModel().ok(true).result(result);
  }

  private static AccountRoleReturnModel createAccountRoleReturnModel() {
    AccountRoleReturnModelResult result =
        new AccountRoleReturnModelResult()
            .id(uuid)
            .role(RoleEnum.CLIENT.getValue())
            .status(StatusEnum.ACTIVE.getValue());
    return new AccountRoleReturnModel().ok(true).result(result);
  }

  private static ReturnModelResult createReturnModelResult() {
    ReturnModelResult result =
        new ReturnModelResult().email("testmail123@gmail.com").username("testusername").id(uuid);
    return result;
  }

  private static AccountRoleReturnModelResult createAccountRoleReturnModelResult() {
    AccountRoleReturnModelResult result =
        new AccountRoleReturnModelResult()
            .id(uuid)
            .role(RoleEnum.CLIENT.getValue())
            .status(StatusEnum.ACTIVE.getValue());
    return result;
  }

  private static AccountRoleIDReturnModelResult createAccountRoleIDReturnModelResult() {
    AccountRoleIDReturnModelResult result = new AccountRoleIDReturnModelResult().roleId(uuid);
    return result;
  }

  @Test
  void testConvertRequestModelToAccount() {
    RequestModel requestModel = createRequestModel();
    Account account = createAccount();
    when(modelMapper.map(requestModel, Account.class)).thenReturn(account);
    Account returnAccount = entityConverterService.convertRequestModelToAccount(requestModel);
    verify(modelMapper).map(any(), any());
    assertEquals(returnAccount.getEmail(), requestModel.getEmail());
    assertEquals(returnAccount.getUsername(), requestModel.getUsername());
    assertEquals(returnAccount.getPassword(), requestModel.getPassword());
  }

  @Test
  void testConvertAccountToReturnModel() {
    Account account = createAccount();
    ReturnModelResult result = createReturnModelResult();
    when(modelMapper.map(account, ReturnModelResult.class)).thenReturn(result);
    ReturnModel returnModel = entityConverterService.convertAccountToReturnModel(account);
    verify(modelMapper).map(any(), any());
    assertEquals(returnModel.isOk(), true);
    assertEquals(returnModel.getResult().getId(), account.getId());
    assertEquals(returnModel.getResult().getUsername(), account.getUsername());
    assertEquals(returnModel.getResult().getEmail(), account.getEmail());
  }

  @Test
  void testConvertRequestmodelToAccountRole() {
    AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
    AccountRole accountRole = createAccountRole();
    when(modelMapper.map(accountRoleRequestModel, AccountRole.class)).thenReturn(accountRole);
    AccountRole returnAccountRole =
        entityConverterService.convertRequestmodelToAccountRole(uuid, accountRoleRequestModel);
    verify(modelMapper).map(any(), any());
    assertEquals(returnAccountRole.getAccountID(), uuid);
  }

  @Test
  void testConvertAccountRoleToReturnModel() {
    AccountRole accountRole = createAccountRole();
    AccountRoleReturnModelResult accountRoleReturnModelResult =
        createAccountRoleReturnModelResult();
    when(modelMapper.map(accountRole, AccountRoleReturnModelResult.class))
        .thenReturn(accountRoleReturnModelResult);
    AccountRoleReturnModel returnModel =
        entityConverterService.convertAccountRoleToReturnModel(accountRole);
    verify(modelMapper).map(any(), any());
    assertEquals(returnModel.isOk(), true);
    assertEquals(returnModel.getResult().getId(), accountRole.getId());
    assertEquals(returnModel.getResult().getRole(), accountRole.getRole());
    assertEquals(returnModel.getResult().getStatus(), accountRole.getStatus());
  }

  @Test
  void testConvertAccountRoleToIdReturnModel() {
    AccountRole accountRole = createAccountRole();
    AccountRoleIDReturnModelResult accountRoleIDReturnModelResult =
        createAccountRoleIDReturnModelResult();
    when(modelMapper.map(accountRole, AccountRoleIDReturnModelResult.class))
        .thenReturn(accountRoleIDReturnModelResult);
    AccountRoleIDReturnModel returnModel =
        entityConverterService.convertAccountRoleToIdReturnModel(accountRole);
    verify(modelMapper).map(any(), any());
    assertEquals(returnModel.isOk(), true);
    assertEquals(returnModel.getResult().getRoleId(), accountRole.getId());
  }
}
