package com.account.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.account.service.AddRandomSuffixToUsernameService;

@SpringBootTest
public class AddRandomSuffixToUsernameServiceTest {

    @Autowired
    AddRandomSuffixToUsernameService addRandomSuffixToUsernameService;

    @Test
    void testAddRandomSuffixToUsername() {
        String username = "username";
        String usernameWithRandomSuffix = addRandomSuffixToUsernameService.addRandomSuffixToUsername(username);
        assertNotEquals(username, usernameWithRandomSuffix);
    }
}
