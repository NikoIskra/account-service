package com.account.controller.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.account.controller.RoleApi;
import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.model.custom.Tuple2;
import com.account.service.AccountRoleService;

import jakarta.validation.Valid;

@RestController
public class AccountRoleController implements RoleApi {

    @Autowired
    private AccountRoleService accountRoleService;

    @Override
    public ResponseEntity<AccountRoleReturnModel> apiV1AccountAccountIdRolePost(
            @PathVariable("account-id") UUID accountId,
            @Valid AccountRoleRequestModel accountRoleRequestModel) throws Exception {
        Tuple2<AccountRoleReturnModel, Boolean> tuple2 = accountRoleService.save(accountId, accountRoleRequestModel);
        HttpHeaders returnHeaders = new HttpHeaders();
        AccountRoleReturnModel accountRoleReturnModel = (AccountRoleReturnModel) tuple2.getFirst();
        String headerString = "/api/v1/account/" + accountId.toString() + "/role/"
                + accountRoleReturnModel.getResult().getRole();
        returnHeaders.set("Location", headerString);
        Integer statusCode = tuple2.getSecond() ? 200 : 201;
        return ResponseEntity.status(HttpStatus.valueOf(statusCode)).headers(returnHeaders).body(accountRoleReturnModel);
    }

    @Override
    public ResponseEntity<AccountRoleIDReturnModel> apiV1AccountAccountIdRoleRoleGet(UUID accountId, String role)
            throws Exception {
        AccountRoleIDReturnModel accountRoleIDReturnModel = accountRoleService.get(accountId, role);
        return ResponseEntity.status(HttpStatus.OK).body(accountRoleIDReturnModel);
    }
}
