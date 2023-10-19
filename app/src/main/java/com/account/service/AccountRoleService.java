package com.account.service;

import com.account.model.AccountRoleIDReturnModel;
import com.account.model.AccountRoleRequestModel;
import com.account.model.RoleEnum;
import com.account.model.custom.Tuple2;
import java.util.UUID;

public interface AccountRoleService {
  Tuple2 save(UUID accountID, AccountRoleRequestModel accountRoleRequestModel);

  AccountRoleIDReturnModel get(UUID accountId, RoleEnum role);
}
