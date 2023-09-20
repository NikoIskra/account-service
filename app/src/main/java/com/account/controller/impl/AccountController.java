package com.account.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.account.controller.AccountApi;
import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.service.AccountService;

@RestController
public class AccountController implements AccountApi {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<ReturnModel> apiV1AccountPost(RequestModel requestModel) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(requestModel));
    }
    
}
