package com.account.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDTO {
    private final Boolean ok;

    private final Result result;

    private final String errorMessage;


}
