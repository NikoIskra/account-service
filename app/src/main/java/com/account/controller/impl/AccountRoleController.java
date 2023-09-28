package com.account.controller.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.account.controller.RoleApi;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.service.AccountRoleService;

import jakarta.validation.Valid;

@RestController
public class AccountRoleController implements RoleApi {

    @Autowired
    private AccountRoleService accountRoleService;

    @Override
    public ResponseEntity<AccountRoleReturnModel> apiV1AccountAccountIdRolePost(@PathVariable("account-id") UUID accountId,
            @Valid AccountRoleRequestModel accountRoleRequestModel) throws Exception {
        Boolean accountExists = accountRoleService.existsByAccountIDAndRole(accountId, accountRoleRequestModel.getRole().getValue());
        AccountRoleReturnModel accountRoleReturnModel = accountRoleService.save(accountId, accountRoleRequestModel);
        HttpHeaders returnHeaders = new HttpHeaders();
        String headerString = "/api/v1/account/" + accountId.toString() + "/role/" + accountRoleReturnModel.getResult().getRole();
        returnHeaders.set("Location", headerString);
        if (accountExists) 
        return ResponseEntity.status(HttpStatus.OK).headers(returnHeaders).body(accountRoleReturnModel);
        else
        return ResponseEntity.status(HttpStatus.CREATED).headers(returnHeaders).body(accountRoleReturnModel);
    }
}
