package com.qroo.kyc.controllers;


import com.qroo.common.data.constants.Status;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import com.qroo.kyc.services.AccountsService;
import com.qroo.kyc.services.OrganizationsService;
import com.qroo.kyc.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class UsersController {
    @Autowired
    UserService service;
    @Autowired
    AccountsService accountService;
    @Autowired
    OrganizationsService organizationsService;
    private final Logger logger;

    public UsersController() {
        logger = LoggerFactory.getLogger(UsersController.class);
    }

    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    public Page<User> searchUsers(SearchRequest request){
        return service.searchUsers(request);
    }

    public List<User> getUser(String uid){
        return service.getByUid(uid);
    }

    public User createUser(User user){
        User createdUser = null;
        try{
            createdUser = service.createUser(user);
            //Create account if user created successfully
            if ( createdUser != null ){
                Account account = new Account();
                account.setMobileNumber(createdUser.getMobileNumber());
                account.setTotalAccrued(BigDecimal.valueOf(0));
                account.setCurrentBalance(BigDecimal.valueOf(0));
                account.setTotalSpent(BigDecimal.valueOf(0));
                account.setUser(createdUser);
                try{
                    accountService.createAccount(account);
                }catch(Exception e){
                    logger.error("Exception while creating user account: {}", e.getMessage());
                }
            }
        }catch(Exception e){
            logger.error("Exception while creating user: {}", e.getMessage());
        }

        return createdUser;
    }

    public User modifyUser(Long id, User user, String action){
        if (action == "DELETE"){
            user.setStatus(Status.DELETED);
        }
        return service.updateUser(id, user);
    }

    public Account getUserAccounts(Long id){
        User userObj = null;
        Account userAcc = null;
        try{
            userObj = service.getById(id);
            if ( userObj != null ){
                try{
                    userAcc = accountService.getByUser(userObj);
                }catch(Exception e){
                    logger.error("Exception while fetching user account: {}", e.getMessage());
                }
            }
        }catch(Exception e){
            logger.error("Exception while fetching user account: {}", e.getMessage());
        }
        return userAcc;
    }

    public List<Organization> getMyOrganizations(Long id){
        User userObj = null;
        List<Organization> userOrganizations = null;
        try{
            userObj = service.getById(id);
            if ( userObj != null ){
                try{
                    userOrganizations = organizationsService.getByOrganizationOwner(userObj);
                }catch(Exception e){
                    logger.error("Exception while fetching user organizations: {}", e.getMessage());
                }
            }
        }catch(Exception e){
            logger.error("Exception while fetching user organizations: {}", e.getMessage());
        }
        return userOrganizations;
    }

    public Organization getUserOrganization(Long id){
        User userObj = null;
        Organization userOrganization = null;
        try{
            userObj = service.getById(id);
            if ( userObj != null ){
                try{
                    if ( userObj.getOrganization() != null && userObj.getOrganization().getId() != null ) {
                        userOrganization = organizationsService.getById(userObj.getOrganization().getId());
                    }
                }catch(Exception e){
                    logger.error("Exception while fetching user organizations: {}", e.getMessage());
                }
            }
        }catch(Exception e){
            logger.error("Exception while fetching user organizations: {}", e.getMessage());
        }
        return userOrganization;
    }
}