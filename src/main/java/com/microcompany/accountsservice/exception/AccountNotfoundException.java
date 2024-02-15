package com.microcompany.accountsservice.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class AccountNotfoundException extends GlobalException {
    protected static final long serialVersionUID = 2L;

    public AccountNotfoundException() {
        super("Account not found");
    }

    public AccountNotfoundException(Long accountId) {
        super("Account with id: " + accountId + " not found");
    }
}
