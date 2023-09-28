package com.account.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRoleRepository;
import com.account.service.AccountRoleService;
import com.account.service.AccountRoleValidator;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountRoleServiceImpl implements AccountRoleService {

    private final AccountRoleRepository accountRoleRepository;

    private final EntityManager entityManager;

    private final AccountRoleValidator accountRoleValidator;

    public AccountRole mapRequestModelToAccountRole(UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        AccountRole accountRole = new AccountRole(accountID, accountRoleRequestModel.getRole().toString(),
                accountRoleRequestModel.getStatus().toString());
        return accountRole;
    }

    public AccountRoleReturnModel mapAccountRoleToReturnModel(AccountRole accountRole) {
        AccountRoleReturnModelResult returnModelResult;
        returnModelResult = new AccountRoleReturnModelResult()
                .id(accountRole.getId())
                .role(accountRole.getRole())
                .status(accountRole.getStatus());

        if (accountRole.getCreatedAt() != null) 
            returnModelResult.setCreatedAt(accountRole.getCreatedAt().getTime());
        if (accountRole.getUpdatedAt() != null) 
            returnModelResult.setUpdatedAt(accountRole.getUpdatedAt().getTime());
        return new AccountRoleReturnModel().ok(true).result(returnModelResult);
    }

    public Boolean accountExists(UUID accountID, String role) {
        return accountRoleRepository.existsByAccountIDAndRole(accountID, role);
    }

    @Override
    @Transactional
    public AccountRoleReturnModel save(UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        UUID accountRoleToUpdateID = accountRoleValidator.validateAccountRoleRequestData(accountID,
                accountRoleRequestModel);
        if (accountRoleToUpdateID != null) {
            AccountRole accountRoleToUpdate = accountRoleRepository.getById(accountRoleToUpdateID);
            accountRoleToUpdate.setStatus(accountRoleRequestModel.getStatus().getValue());
            accountRoleRepository.saveAndFlush(accountRoleToUpdate);
            return mapAccountRoleToReturnModel(accountRoleToUpdate);
        }
        AccountRole accountRole = accountRoleRepository
                .saveAndFlush(
                    mapRequestModelToAccountRole(accountID, accountRoleRequestModel)
                    );
        entityManager.refresh(accountRole);
        return mapAccountRoleToReturnModel(accountRole);
    }

    @Override
    public Boolean existsByAccountIDAndRole(UUID accountId, String role) {
        return accountRoleRepository.existsByAccountIDAndRole(accountId, role);
    }
}
