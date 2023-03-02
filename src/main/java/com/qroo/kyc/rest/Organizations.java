package com.qroo.kyc.rest;

import com.qroo.common.data.constants.Status;
import com.qroo.common.data.dto.AccountDto;
import com.qroo.common.data.dto.OrganizationDto;
import com.qroo.common.data.dto.OrganizationResponseDto;
import com.qroo.common.utility.ApiResponse;
import com.qroo.kyc.controllers.OrganizationsController;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PostMapping(value = "/organizations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Page<Organization>>> search(@RequestBody SearchRequest request) {
        ApiResponse<Page<Organization>> responseObject = new ApiResponse<>();
        try {
            Page<Organization> queryResponse = organizationsController.searchOrganizations(request);

            if( ObjectUtils.isEmpty(queryResponse) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No Response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(queryResponse);
            responseObject.setMessage("Organizations fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch (Exception e) {
            logger.error("Exception while fetching organizations: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/organization/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<OrganizationResponseDto>> fetchOrganization(@PathVariable Long id) {
        ModelMapper modelMapper = new ModelMapper();
        ApiResponse<OrganizationResponseDto> responseObject = new ApiResponse<>();

        OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
        OrganizationDto organizationResponse = new OrganizationDto();
        AccountDto accountResponse = new AccountDto();

        try {
            //Get data
            Organization organization = organizationsController.getOrganization(id);
            Account account = organizationsController.getOrganizationAccounts(organization.getId());

            //Map data to DTO
            if ( organization != null ){
                organizationResponse = modelMapper.map(organization, OrganizationDto.class);
            }

            if ( account != null ){
                accountResponse = modelMapper.map(account, AccountDto.class);
            }

            //Build response
            organizationResponseDto.setOrganization(organizationResponse);
            organizationResponseDto.setAccount(accountResponse);

            if( ObjectUtils.isEmpty(organizationResponse) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizationResponseDto);
            responseObject.setMessage("Organization fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching organizations: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/organization/getAlias/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<String>> getAlias(@PathVariable Long id) {
        String alias = null;
        ApiResponse<String> responseObject = new ApiResponse<>();
        try {
            //Get data
            Organization organization = organizationsController.getOrganization(id);
            alias = organization.getAlias();

            if( ObjectUtils.isEmpty(organization) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(alias);
            responseObject.setMessage("Organization alias fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching organization alias: {}", e.toString());
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
            Organization organizationUpdated = organizationsController.modifyOrganization(id, organization);

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

    @PostMapping(value ="/organization/deactivate/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Organization>> deactivateOrganization(@PathVariable Long id) {
        ApiResponse<Organization> responseObject = new ApiResponse<>();
        try {
            Organization organizationUpdated  = organizationsController.modifyOrganization(id, Status.INACTIVE);

            if( ObjectUtils.isEmpty(organizationUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from organizations service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizationUpdated);
            responseObject.setMessage("Organization deactivated successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while deactivating organization: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value ="/organization/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Organization>> deleteOrganization(@PathVariable Long id) {
        ApiResponse<Organization> responseObject = new ApiResponse<>();
        try {
            Organization organizationUpdated  = organizationsController.modifyOrganization(id, Status.DELETED);

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
}
