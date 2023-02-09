package com.qroo.kyc.rest;

import com.qroo.common.utility.ApiResponse;
import com.qroo.kyc.controllers.AccountsController;
import com.qroo.kyc.data.vo.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kyc")
public class Accounts {
    @Autowired
    AccountsController accountsController;
    private final Logger logger;

    public Accounts() {
        logger = LoggerFactory.getLogger(Accounts.class);
    }

    @GetMapping(value ="/accounts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<Account>>> fetchAccounts() {
        ApiResponse<List<Account>> responseObject = new ApiResponse<>();
        try {
            List<Account> account = accountsController.getAllAccounts();

            if( ObjectUtils.isEmpty(account) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No Response from accounts service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(account);
            responseObject.setMessage("Accounts fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching accounts: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/account/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Account>> fetchAccount(@PathVariable Long id) {
        ApiResponse<Account> responseObject = new ApiResponse<>();
        try {
            Account account = accountsController.getAccount(id);

            if( ObjectUtils.isEmpty(account) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from accounts service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(account);
            responseObject.setMessage("Account fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching accounts: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    //TODO: Remove this method, there should be no way to create account unless you do it while creating a new user
//    @PostMapping(value ="/account/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody Account account) {
//        ApiResponse<Account> responseObject = new ApiResponse<>();
//        try {
//            Account accountCreated = accountsController.createAccount(account);
//
//            if( ObjectUtils.isEmpty(accountCreated) ) {
//                responseObject.setCode("400");
//                responseObject.setMessage("No response from accounts service");
//            }
//            responseObject.setCode("200");
//            responseObject.setPayload(accountCreated);
//            responseObject.setMessage("Account created successfully");
//            return new ResponseEntity<>(responseObject, HttpStatus.OK);
//        }catch(Exception e) {
//            logger.error("Exception while creating account: {}", e.toString());
//            responseObject.setMessage(e.getMessage());
//            responseObject.setCode("500");
//            return new ResponseEntity<>(responseObject, HttpStatus.OK);
//        }
//    }


    @PutMapping(value ="/account/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Account>> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        ApiResponse<Account> responseObject = new ApiResponse<>();
        try {
            Account accountUpdated = accountsController.modifyAccount(id, account,"UPDATE");

            if( ObjectUtils.isEmpty(accountUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from accounts service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(accountUpdated);
            responseObject.setMessage("Account updated successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while updating accounts: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PutMapping(value ="/account/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Account>> deleteAccount(@PathVariable Long id, @RequestBody Account account) {
        ApiResponse<Account> responseObject = new ApiResponse<>();
        try {
            Account accountUpdated = accountsController.modifyAccount(id, account, "DELETE");

            if( ObjectUtils.isEmpty(accountUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from accounts service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(accountUpdated);
            responseObject.setMessage("Account deleted successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while deleting account: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

}
