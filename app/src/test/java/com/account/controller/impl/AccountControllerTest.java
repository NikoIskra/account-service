package com.account.controller.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.account.exception.BadRequestException;
import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.service.impl.AccountServiceImpl;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean AccountServiceImpl accountService;

  RequestModel emptyRequestModel = new RequestModel();

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static RequestModel createRequestModel() {
    RequestModel validRequestModel =
        new RequestModel()
            .email("testmail@gmail.com")
            .username("testusername")
            .password("testpassword");
    return validRequestModel;
  }

  private static ReturnModel createReturnModel() {
    ReturnModelResult result =
        new ReturnModelResult().id(uuid).email("testmail@gmail.com").username("testusername");
    return new ReturnModel().ok(true).result(result);
  }

  ObjectMapper mapper = new ObjectMapper();

  @Test
  void testInsertValidAccount() throws Exception {
    RequestModel requestModel = createRequestModel();
    ReturnModel returnModel = createReturnModel();
    when(accountService.save(requestModel)).thenReturn(returnModel);
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestModel)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true));
  }

  @Test
  void testInsetAccount_badRequest() throws Exception {
    RequestModel requestModel = createRequestModel();
    when(accountService.save(requestModel)).thenThrow(new BadRequestException("exception"));
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestModel)))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false));
  }

  @Test
  void testInsertEmptyAccount() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emptyRequestModel)))
        .andExpect(status().isBadRequest());
  }
}
