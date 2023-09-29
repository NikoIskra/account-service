package com.account.test.account_tests.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.account.service.RandomTextGenerator;

public class RandomTextGeneratorTest {

    @Test
    void testAddRandomSuffixToUsername() {
        String username = "username";
        String usernameWithRandomSuffix = RandomTextGenerator.addRandomSuffixToUsername(username);
        assertNotEquals(username, usernameWithRandomSuffix);
    }
}
