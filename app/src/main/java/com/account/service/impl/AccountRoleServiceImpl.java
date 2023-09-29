package com.account.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.account.exception.BadRequestException;
import com.account.model.AccountRoleRequestModel;
import com.account.model.AccountRoleReturnModel;
import com.account.model.AccountRoleReturnModelResult;
import com.account.model.custom.Tuple2;
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

    private AccountRole mapRequestModelToAccountRole(UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        AccountRole accountRole = new AccountRole(accountID, accountRoleRequestModel.getRole().toString(),
                accountRoleRequestModel.getStatus().toString());
        return accountRole;
    }

    private AccountRoleReturnModel mapAccountRoleToReturnModel(AccountRole accountRole) {
        AccountRoleReturnModelResult returnModelResult;
        returnModelResult = new AccountRoleReturnModelResult()
                .id(accountRole.getId())
                .role(accountRole.getRole())
                .status(accountRole.getStatus());

        if (accountRole.getCreatedAt() != null) {
            returnModelResult.setCreatedAt(accountRole.getCreatedAt().getTime());
        }
        if (accountRole.getUpdatedAt() != null) {
            returnModelResult.setUpdatedAt(accountRole.getUpdatedAt().getTime());
        }
        return new AccountRoleReturnModel().ok(true).result(returnModelResult);
    }

    @Override
    @Transactional
    public Tuple2<AccountRoleReturnModel, Boolean> save(UUID accountID, AccountRoleRequestModel accountRoleRequestModel) {
        Tuple2<AccountRoleReturnModel, Boolean> returnTuple = new Tuple2<AccountRoleReturnModel,Boolean>(null, true);
        accountRoleValidator.validateAccountRoleRequestData(accountID, accountRoleRequestModel);
        List<AccountRole> accountRoles = accountRoleRepository.findAll();
        Optional<AccountRole> accountRoleFilterByIDRoleAndStatus = accountRoles.stream()
            .filter(
                a -> a.getAccountID().equals(accountID) && a.getRole().equals(accountRoleRequestModel.getRole().getValue()) && a.getStatus().equals(accountRoleRequestModel.getStatus().getValue())
            )
            .findFirst();
        Optional<AccountRole> accountRoleFilteredByIDAndRole = accountRoles.stream()
            .filter(
                a -> a.getAccountID().equals(accountID) && a.getRole().equals(accountRoleRequestModel.getRole().getValue())
            )
            .findFirst();
        if (accountRoleFilterByIDRoleAndStatus.isPresent()) {
            AccountRole accountRoleToReturn = accountRoleFilterByIDRoleAndStatus.get();
            entityManager.refresh(accountRoleToReturn);
            returnTuple.setFirst(mapAccountRoleToReturnModel(accountRoleToReturn));
            return returnTuple;
        }
        else if (accountRoleFilteredByIDAndRole.isPresent()) {
            AccountRole accountRoleToUpdate = accountRoleFilteredByIDAndRole.get();
            accountRoleToUpdate.setStatus(accountRoleRequestModel.getStatus().getValue());
            accountRoleRepository.saveAndFlush(accountRoleToUpdate);
            entityManager.refresh(accountRoleToUpdate);
            returnTuple.setFirst(mapAccountRoleToReturnModel(accountRoleToUpdate));
            return returnTuple;
        } else {
            AccountRole accountRole = accountRoleRepository
                    .saveAndFlush(
                            mapRequestModelToAccountRole(accountID, accountRoleRequestModel));
            entityManager.refresh(accountRole);
            returnTuple.setFirst(mapAccountRoleToReturnModel(accountRole));
            returnTuple.setSecond(false);
            return returnTuple;
        }
    }
}
