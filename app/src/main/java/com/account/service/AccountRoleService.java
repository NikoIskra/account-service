package com.account.service;

import java.util.UUID;

import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.model.custom.Tuple2;

public interface AccountRoleService {
    Tuple2 save (UUID accountID, AccountRoleRequestModel accountRoleRequestModel);

    AccountRoleIDReturnModel get (UUID accountId, String role);
}
