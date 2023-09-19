package com.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {


    private Boolean ok;

    private String errorMessage;
}
