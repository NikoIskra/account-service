package com.account.controller.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.account.exception.BadRequestException;
import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleIDReturnModelResult;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.model.RoleEnum;
import com.account.model.custom.Tuple2;
import com.account.service.impl.AccountRoleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AccountRoleController.class)
@AutoConfigureMockMvc
public class AccountRoleControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean AccountRoleServiceImpl accountRoleServiceImpl;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  AccountRoleRequestModel emptyAccountRoleRequestModel = new AccountRoleRequestModel();

  private static AccountRoleRequestModel createAccountRoleRequestModel() {
    AccountRoleRequestModel validAccountRoleRequestModel =
        new AccountRoleRequestModel().role(RoleEnum.MANAGER).status(StatusEnum.REVOKED);
    return validAccountRoleRequestModel;
  }

  private static AccountRoleReturnModel createAccountRoleReturnModel() {
    AccountRoleReturnModelResult result =
        new AccountRoleReturnModelResult()
            .id(uuid)
            .role(RoleEnum.MANAGER.toString())
            .status(StatusEnum.REVOKED.toString());
    return new AccountRoleReturnModel().ok(true).result(result);
  }

  private static AccountRoleIDReturnModel createAccountRoleIDReturnModel() {
    AccountRoleIDReturnModelResult result = new AccountRoleIDReturnModelResult().roleId(uuid);
    return new AccountRoleIDReturnModel().ok(true).result(result);
  }

  ObjectMapper mapper = new ObjectMapper();

  @Test
  void testInsertAccountRole_ok() throws Exception {
    AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
    AccountRoleReturnModel accountRoleReturnModel = createAccountRoleReturnModel();
    Tuple2 tuple2 = new Tuple2<AccountRoleReturnModel, Boolean>(accountRoleReturnModel, true);
    when(accountRoleServiceImpl.save(uuid, accountRoleRequestModel)).thenReturn(tuple2);
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/account/" + uuid + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRoleRequestModel)))
        .andExpect(status().isOk());
  }

  @Test
  void testInsertAccountRole_created() throws Exception {
    AccountRoleRequestModel accountRoleRequestModel = createAccountRoleRequestModel();
    AccountRoleReturnModel accountRoleReturnModel = createAccountRoleReturnModel();
    Tuple2 tuple2 = new Tuple2<AccountRoleReturnModel, Boolean>(accountRoleReturnModel, false);
    when(accountRoleServiceImpl.save(uuid, accountRoleRequestModel)).thenReturn(tuple2);
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/account/" + uuid + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRoleRequestModel)))
        .andExpect(status().isCreated());
  }

  @Test
  void testInsertEmptyAccountRole() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/account/" + uuid + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emptyAccountRoleRequestModel)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetAccountRole() throws Exception {
    AccountRoleIDReturnModel accountRoleIDReturnModel = createAccountRoleIDReturnModel();
    when(accountRoleServiceImpl.get(uuid, RoleEnum.DELIVERY)).thenReturn(accountRoleIDReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/account/" + uuid + "/role/DELIVERY")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testGetAccountRole_badRequest() throws Exception {
    when(accountRoleServiceImpl.get(uuid, RoleEnum.DELIVERY))
        .thenThrow(new BadRequestException(null));
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/account/" + uuid + "/role/DELIVERY")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
