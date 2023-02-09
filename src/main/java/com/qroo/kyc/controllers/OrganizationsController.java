package com.qroo.kyc.controllers;

import com.qroo.common.data.constants.Status;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.services.AccountsService;
import com.qroo.kyc.services.OrganizationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public
class OrganizationsController {
    @Autowired
    OrganizationsService service;
    @Autowired
    AccountsService accountService;
    private final Logger logger;

    public OrganizationsController() {
        logger = LoggerFactory.getLogger(OrganizationsController.class);
    }

    public List<Organization> getAllOrganizations(){
        return service.getAllOrganizations();
    }

    public Organization getOrganization(Long id){
        return service.getById(id);
    }

    public Organization createOrganization(Organization organization){
        Organization createdOrganization = null;
        try{
            createdOrganization = service.createOrganization(organization);
            //Create account if organization created successfully
            if ( createdOrganization != null ){
                Account account = new Account();
                account.setMobileNumber(createdOrganization.getMobileNumber());
                account.setTotalAccrued(BigDecimal.valueOf(0));
                account.setCurrentBalance(BigDecimal.valueOf(0));
                account.setTotalSpent(BigDecimal.valueOf(0));
                account.setOrganization(createdOrganization);

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

    public Organization modifyOrganization(Long id, Organization organization, String action){
        if (action == "DELETE"){
            organization.setStatus(Status.DELETED);
        }
        return service.updateOrganization(id, organization);
    }

    public List<Account> getOrganizationAccounts(Long id){
        Organization organizationObj = null;
        List<Account> organizationAcc = null;
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
}