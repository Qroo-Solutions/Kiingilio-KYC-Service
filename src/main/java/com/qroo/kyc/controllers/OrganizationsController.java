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
public
class OrganizationsController {
    @Autowired
    OrganizationsService service;
    @Autowired
    UserService userService;
    @Autowired
    AccountsService accountService;
    private final Logger logger;

    public OrganizationsController() {
        logger = LoggerFactory.getLogger(OrganizationsController.class);
    }

    public List<Organization> getAllOrganizations(){
        return service.getAllOrganizations();
    }

    public Page<Organization> searchOrganizations(SearchRequest request){
        return service.searchOrganizations(request);
    }

    public Organization getOrganization(Long id){
        return service.getById(id);
    }

    public Organization createOrganization(Organization organization){
        Organization createdOrganization = null;
        try{
            User user = userService.getByUid(organization.getUser().getUid());
            organization.setUser(user);

            String alias = generateAlias(organization.getName(), 0);
            organization.setAlias(alias);

            createdOrganization = service.createOrganization(organization);

            //Create account if organization created successfully
            if ( createdOrganization != null ){
                Account account = new Account();
                account.setMobileNumber(createdOrganization.getMobileNumber());
                account.setTotalAccrued(BigDecimal.valueOf(0));
                account.setCurrentBalance(BigDecimal.valueOf(0));
                account.setTotalSpent(BigDecimal.valueOf(0));
                account.setOrganization(createdOrganization);
                account.setUser(user);
                try{
                    accountService.createAccount(account);
                }catch(Exception e){
                    logger.error("Exception while creating organization account: {}", e.getMessage());
                }
            }
        }catch(Exception e){
            logger.error("Exception while organization user: {}", e.getMessage());
        }

        return createdOrganization;
    }

    public Organization modifyOrganization(Long id, Organization organization){
        return service.updateOrganization(id, organization);
    }

    public Organization modifyOrganization(Long id, Status status){
        Organization organizationToDelete = service.getById(id);
        if ( organizationToDelete != null){
            organizationToDelete.setStatus(status);
        }
        return service.updateOrganization(id, organizationToDelete);
    }

    public Account getOrganizationAccounts(Long id){
        Organization organizationObj = null;
        Account organizationAcc = null;
        try{
            organizationObj = service.getById(id);
            if ( organizationObj != null ){
                try{
                    organizationAcc = accountService.getByOrganization(organizationObj);
                }catch(Exception e){
                    logger.error("Exception while fetching organization account: {}", e.getMessage());
                }
            }
        }catch(Exception e){
            logger.error("Exception while fetching organization account: {}", e.getMessage());
        }
        return organizationAcc;
    }

    public String generateAlias(String str, Integer increment){
        String alias = null;
        try{
            alias = str.toLowerCase().strip().replaceAll("\\s+","-");

            Organization aliasResult = service.getByAlias(alias);
            if ( aliasResult != null ){
                Integer nextIncrement = increment+1;
                String nextStr = str+nextIncrement;
                //Alias found, add number at the end and retry
                return generateAlias(nextStr, nextIncrement);            }
        }catch(Exception e){
            logger.error("Exception while fetching events: {}", e.toString());
        }
        return alias;
    }
}