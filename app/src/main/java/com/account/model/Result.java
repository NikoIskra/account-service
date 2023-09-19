package com.account.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private final UUID id;
    private final String email;
    private final String username;
    private final String status;
    private final Long createdAt;
}
