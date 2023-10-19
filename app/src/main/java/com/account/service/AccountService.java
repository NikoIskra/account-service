package com.account.service;

import com.account.model.RequestModel;
import com.account.model.ReturnModel;

public interface AccountService {
  ReturnModel save(RequestModel account);
}
