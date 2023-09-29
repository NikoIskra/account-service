package com.account.test.AccountRoleTests.ControllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.RoleEnum;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.model.RequestModel;
import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountRoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    @Autowired
    AccountRepository accountRepository;


    AccountRoleRequestModel emptyAccountRoleRequestModel = new AccountRoleRequestModel();

    AccountRoleRequestModel validAccountRoleRequestModel = new AccountRoleRequestModel()
            .role(RoleEnum.MANAGER)
            .status(StatusEnum.REVOKED);

    RequestModel validAccountRequestModel = new RequestModel()
            .email("testmail123@gmail.com")
            .username("testusername")
            .password("testpassword");


    ObjectMapper mapper = new ObjectMapper();

    void insertAccountToDB() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(validAccountRequestModel)))
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void testInsertEmptyAccountRole() throws Exception {
        insertAccountToDB();
        Account account = accountRepository.findByUsername("testusername").get();
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/account/" + account.getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emptyAccountRoleRequestModel)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertAccountRoleWithNoAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/account/26bea1b4-ea70-4df2-836b-3ee2f5cece13/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(validAccountRoleRequestModel)))
                .andExpect(status().isNotFound());
    }
}
