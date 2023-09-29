package com.account.model;

public class AccountRoleBooleanTuple<AccountRole, Boolean> {
    private AccountRole accountRole;
    private Boolean bool;
    
    public AccountRoleBooleanTuple(AccountRole accountRole, Boolean bool) {
        this.accountRole = accountRole;
        this.bool = bool;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }


}
