package com.account.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleIDReturnModelResult;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.model.RequestModel;
import com.account.model.ReturnModel;
import com.account.model.ReturnModelResult;
import com.account.persistence.entity.Account;
import com.account.persistence.entity.AccountRole;

@Service
public class EntityConverterService {
    @Autowired
    private ModelMapper modelMapper;

    public Account convertRequestModelToAccount (RequestModel requestModel) {
        return modelMapper.map(requestModel, Account.class);
    }

    public ReturnModel convertAccountToReturnModel (Account account) {
        ReturnModelResult result = modelMapper.map(account, ReturnModelResult.class);
        return new ReturnModel().ok(true).result(result);
    }

    public AccountRole convertRequestmodelToAccountRole (UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        AccountRole accountRole = modelMapper.map(accountRoleRequestModel, AccountRole.class);
        accountRole.setAccountID(accountID);
        return accountRole;
    }

    public AccountRoleReturnModel convertAccountRoleToReturnModel (AccountRole accountRole) {
        AccountRoleReturnModelResult accountRoleReturnModelResult = modelMapper.map(accountRole, AccountRoleReturnModelResult.class);
        return new AccountRoleReturnModel().ok(true).result(accountRoleReturnModelResult);
    }

    public AccountRoleIDReturnModel convertAccountRoleToIdReturnModel (AccountRole accountRole) {
        AccountRoleIDReturnModelResult accountRoleIDReturnModelResult = modelMapper.map(accountRole, AccountRoleIDReturnModelResult.class);
        return new AccountRoleIDReturnModel().ok(true).result(accountRoleIDReturnModelResult);
    }
}
