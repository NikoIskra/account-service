package com.account.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.account.exception.BadRequestException;
import com.account.exception.NotFoundException;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleRequestModel.StatusEnum;
import com.account.persistence.entity.AccountRole;
import com.account.persistence.repository.AccountRepository;
import com.account.persistence.repository.AccountRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountRoleValidator {

    private final AccountRoleRepository accountRoleRepository;

    private final AccountRepository accountRepository;

    public void validateAccountRoleRequestData(UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        Boolean accountExists = accountRepository.existsById(accountID);
        if (!accountExists) {
            throw new NotFoundException("no account with that ID found!");
        }
        if (accountRoleRequestModel.getStatus().equals(StatusEnum.REVOKED)) {
            if (!accountRoleRepository.existsByAccountIDAndRole(accountID, accountRoleRequestModel.getRole().getValue())) {
                throw new NotFoundException("no account with given role exists to revoke");
            }
        }
    }

}
