package com.account.service;

import java.util.UUID;

import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;

public interface AccountRoleService {
    AccountRoleReturnModel save (UUID accountID, AccountRoleRequestModel accountRoleRequestModel);

    Boolean existsByAccountIDAndRole(UUID accountId, String role);
}
