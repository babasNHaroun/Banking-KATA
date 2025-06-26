package com.banking.model.dto;

public class AccountCreateRequestDTO {
    private String accountId;
    
    public AccountCreateRequestDTO() {}
    
    public AccountCreateRequestDTO(String accountId) {
        this.accountId = accountId;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}