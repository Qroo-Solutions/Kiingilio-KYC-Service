package com.qroo.kyc.rest;

import com.qroo.common.utility.ApiResponse;
import com.qroo.kyc.controllers.OrganizationsController;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kyc")
public class Organizations {
    @Autowired
    OrganizationsController organizationsController;
    private final Logger logger;

    public Organizations() {
        logger = LoggerFactory.getLogger(Organizations.class);
    }

    @GetMapping(value ="/organizations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<Organization>>> fetchOrganizations() {
        ApiResponse<List<Organization>> responseObject = new ApiResponse<>();
        try {
            List<Organization> organizations = organizationsController.getAllOrganizations();

            if( ObjectUtils.isEmpty(organizations) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No Response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizations);
            responseObject.setMessage("Organizations fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching organizations: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/organization/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Organization>> fetchOrganization(@PathVariable Long id) {
        ApiResponse<Organization> responseObject = new ApiResponse<>();
        try {
            Organization organization = organizationsController.getOrganization(id);

            if( ObjectUtils.isEmpty(organization) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organization);
            responseObject.setMessage("Organization fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching organization: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value ="/organization/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Organization>> createOrganization(@RequestBody Organization organization) {
        ApiResponse<Organization> responseObject = new ApiResponse<>();
        try {
            Organization organizationCreated = organizationsController.createOrganization(organization);

            if( ObjectUtils.isEmpty(organizationCreated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizationCreated);
            responseObject.setMessage("Organization created successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while creating organization: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }


    @PutMapping(value ="/organization/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Organization>> updateOrganization(@PathVariable Long id, @RequestBody Organization organization) {
        ApiResponse<Organization> responseObject = new ApiResponse<>();
        try {
            Organization organizationUpdated = organizationsController.modifyOrganization(id, organization, "UPDATE");

            if( ObjectUtils.isEmpty(organizationUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizationUpdated);
            responseObject.setMessage("Organization updated successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while updating organization: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PutMapping(value ="/organization/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Organization>> deleteOrganization(@PathVariable Long id, @RequestBody Organization organization) {
        ApiResponse<Organization> responseObject = new ApiResponse<>();
        try {
            Organization organizationUpdated = organizationsController.modifyOrganization(id, organization, "DELETE");

            if( ObjectUtils.isEmpty(organizationUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizationUpdated);
            responseObject.setMessage("Organization deleted successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while deleting organization: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/organization/{id}/accounts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<Account>>> getOrganizationAccounts(@PathVariable Long id) {
        ApiResponse<List<Account>> responseObject = new ApiResponse<>();
        try {
            List<Account> account = organizationsController.getOrganizationAccounts(id);

            if( ObjectUtils.isEmpty(account) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(account);
            responseObject.setMessage("Organization account fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching account: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

}
