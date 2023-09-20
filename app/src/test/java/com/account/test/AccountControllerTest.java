package com.account.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.account.model.RequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    RequestModel emptyRequestModel = new RequestModel();

    RequestModel validRequestModel = new RequestModel()
                                        .email("testmail@gmail.com")
                                        .username("testusername")
                                        .password("testpassword");

    RequestModel requestModelWithoutUsername = new RequestModel("testmail@gmail.com", "testpassword");
    
    ObjectMapper mapper = new ObjectMapper();



    @Test
    @DirtiesContext
    void testInsertValidAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(validRequestModel)))
        .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void testInsertEmptyAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(emptyRequestModel)))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    void testAccountConflict() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .post("/api/v1/account")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(validRequestModel)))
        .andExpect(status().isCreated());
        
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
