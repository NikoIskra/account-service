package com.account.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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

import com.account.model.RequestModel;
import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    RequestModel emptyRequestModel = new RequestModel();

    RequestModel validRequestModel = new RequestModel()
                                        .email("testmail@gmail.com")
                                        .username("testusername")
                                        .password("testpassword");

    RequestModel requestModelWithoutUsername = new RequestModel("testmail2@gmail.com", "testpassword");
    
    ObjectMapper mapper = new ObjectMapper();



    @Test
    void testInsertValidAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(validRequestModel)))
        .andExpect(status().isCreated());
    }

    @Test
    void testAccountRoleExistsForInsertedAccount() throws Exception {
        Optional<Account> account = accountRepository.findByUsername(validRequestModel.getUsername());
        if (account.isPresent()) {
            Account accountGet = account.get();
            assertTrue(accountRoleRepository.existsByAccountID(accountGet.getId()));
        }
    }

    @Test
    void testInsertEmptyAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(emptyRequestModel)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testAccountConflict() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(validRequestModel)))
        .andExpect(status().isConflict());
    }

    @Test
    @DirtiesContext
    void testInsertAccountWithoutUsername() throws Exception {
        String email = requestModelWithoutUsername.getEmail();
        String expectedUsername = email.substring(0, email.indexOf('@'));
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(requestModelWithoutUsername)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.result.username").value(expectedUsername));
    }
}
