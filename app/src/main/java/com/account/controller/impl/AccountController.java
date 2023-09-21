package com.account.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
        ReturnModel returnModel = accountService.save(requestModel);
        HttpHeaders returnHeaders = new HttpHeaders();
        returnHeaders.set("Location", returnModel.getResult().getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).headers(returnHeaders).body(returnModel);
    }
    
}
