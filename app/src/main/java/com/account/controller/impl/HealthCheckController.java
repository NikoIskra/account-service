package com.account.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.account.controller.DefaultApi;

@RestController
public class HealthCheckController implements DefaultApi {
    @Override
    public ResponseEntity<Void> apiV1HealthcheckGet() throws Exception {
        return ResponseEntity.ok().build();
    }
}
