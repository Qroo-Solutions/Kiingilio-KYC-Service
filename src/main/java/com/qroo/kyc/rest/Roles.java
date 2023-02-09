package com.qroo.kyc.rest;

import com.qroo.common.data.constants.Status;
import com.qroo.common.utility.ApiResponse;
import com.qroo.kyc.controllers.RolesController;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.vo.Role;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/kyc")
public class Roles {
    @Autowired
    RolesController rolesController;
    private final Logger logger;

    public Roles() {
        logger = LoggerFactory.getLogger(Roles.class);
    }

    @GetMapping(value ="/roles", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<Role>>> fetchRoles() {
        ApiResponse<List<Role>> responseObject = new ApiResponse<>();
        try {
            List<Role> role = rolesController.getAllRoles();

            if( ObjectUtils.isEmpty(role) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No Response from roles service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(role);
            responseObject.setMessage("Roles fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching roles: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/roles", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Page<Role>>> search(@RequestBody SearchRequest request) {
        ApiResponse<Page<Role>> responseObject = new ApiResponse<>();
        try {
            Page<Role> queryResponse = rolesController.searchRoles(request);

            if( ObjectUtils.isEmpty(queryResponse) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No Response from roles service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(queryResponse);
            responseObject.setMessage("Roles fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch (Exception e) {
            logger.error("Exception while fetching roles: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/role/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Role>> fetchRole(@PathVariable Long id) {
        ApiResponse<Role> responseObject = new ApiResponse<>();
        try {
            Role role = rolesController.getRole(id);

            if( ObjectUtils.isEmpty(role) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from roles service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(role);
            responseObject.setMessage("Role fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching role: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value ="/role/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Role>> createRole(@RequestBody Role role) {
        ApiResponse<Role> responseObject = new ApiResponse<>();
        try {
            Role roleCreated = rolesController.createRole(role);

            if( ObjectUtils.isEmpty(roleCreated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from role's service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(roleCreated);
            responseObject.setMessage("Role created successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while creating role: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }


    @PutMapping(value ="/role/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable Long id, @RequestBody Role role) {
        ApiResponse<Role> responseObject = new ApiResponse<>();
        try {
            Role roleUpdated = rolesController.modifyRole(id, role);

            if( ObjectUtils.isEmpty(roleUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("Role could not be updated");
            }else{
                responseObject.setCode("200");
                responseObject.setPayload(roleUpdated);
                responseObject.setMessage("Role updated successfully");
            }

            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while updating role: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value ="/role/deactivate/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Role>> deactivateRole(@PathVariable Long id) {
        ApiResponse<Role> responseObject = new ApiResponse<>();
        try {
            Role roleUpdated = rolesController.modifyRole(id, Status.INACTIVE);

            if( ObjectUtils.isEmpty(roleUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("Role could not be deactivated");
            }else{
                responseObject.setCode("200");
                responseObject.setPayload(roleUpdated);
                responseObject.setMessage("Role deactivated successfully");
            }

            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while deactivating role: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value ="/role/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Role>> deleteRole(@PathVariable Long id) {
        ApiResponse<Role> responseObject = new ApiResponse<>();
        try {
            Role roleUpdated = rolesController.modifyRole(id, Status.DELETED);

            if( ObjectUtils.isEmpty(roleUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("Role could not be deleted");
            }else{
                responseObject.setCode("200");
                responseObject.setPayload(roleUpdated);
                responseObject.setMessage("Role deleted successfully");
            }

            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while deleting role: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }
}
