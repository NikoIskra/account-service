package com.account.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCommand {

    @Email
    @NotNull
    private String email;

    @Size (min = 5, message = "Username must be longer than 5 characters!")
    private String username;

    @NotNull
    @Size (min = 8, message = "Username must be longer than 8 characters!")
    @Pattern (regexp = "[A-Za-z0-9]*$")
    private String password;

}
