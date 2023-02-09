package com.qroo.kyc.controllers;

import com.qroo.common.data.constants.Status;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.services.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AccountsController {
    @Autowired
    AccountsService service;
    private final Logger logger;

    public AccountsController() {
        logger = LoggerFactory.getLogger(AccountsController.class);
    }

    public List<Account> getAllAccounts(){
        return service.getAllAccounts();
    }

    public Account getAccount(Long id){
        return service.getById(id);
    }

    public Account createAccount(Account account){
        return service.createAccount(account);
    }

    public Account modifyAccount(Long id, Account account, String action){
        if (action == "DELETE"){
            account.setStatus(Status.DELETED);
        }
        return service.updateAccount(id, account);

    }
}