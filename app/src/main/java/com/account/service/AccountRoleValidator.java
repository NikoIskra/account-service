package com.account.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.account.exception.BadRequestException;
import com.account.exception.NotFoundException;
import com.account.model.AccountRoleRequestModel;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountRoleValidator {

    private final AccountRoleRepository accountRoleRepository;

    public UUID validateAccountRoleRequestData(UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        UUID returnID=null;
        Boolean accountExists = accountRoleRepository.existsByAccountID(accountID);
        if (!accountExists)
            throw new NotFoundException("no account with that ID found!");
        List<AccountRole> accountRoles = accountRoleRepository.findAllByAccountID(accountID);
        for (AccountRole accountRole : accountRoles) {
            if (accountRole.getRole().equals(accountRoleRequestModel.getRole().toString())
                    && accountRole.getStatus().equals(accountRoleRequestModel.getStatus().toString())) {
                throw new BadRequestException("account with that role and status already exists!");
            }
            else if (accountRole.getRole().equals(accountRoleRequestModel.getRole().toString())) {
                returnID=accountRole.getId();
            }
        }
        return returnID;
    }

}
