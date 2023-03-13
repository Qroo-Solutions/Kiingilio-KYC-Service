package com.qroo.kyc.rest;

import com.qroo.common.data.dto.AccountDto;
import com.qroo.common.data.dto.UserDto;
import com.qroo.common.data.dto.UserResponseDto;
import com.qroo.common.utility.ApiResponse;
import com.qroo.kyc.controllers.AccountsController;
import com.qroo.kyc.controllers.UsersController;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/api/kyc")
public class Users {
    @Autowired
    UsersController usersController;
    @Autowired
    AccountsController accountsController;
    private final Logger logger;
    public Users() {
        logger = LoggerFactory.getLogger(Users.class);
    }

    @GetMapping(value ="/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<User>>> fetchUsers() {
        ApiResponse<List<User>> responseObject = new ApiResponse<>();
        try {
            List<User> users = usersController.getAllUsers();

            if( ObjectUtils.isEmpty(users) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No Response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(users);
            responseObject.setMessage("Users fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching users: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Page<User>>> search(@AuthenticationPrincipal @RequestBody SearchRequest request) {
        ApiResponse<Page<User>> responseObject = new ApiResponse<>();
        try {
            Page<User> queryResponse = usersController.searchUsers(request);

            if( ObjectUtils.isEmpty(queryResponse) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(queryResponse);
            responseObject.setMessage("Users fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch (Exception e) {
            logger.error("Exception while fetching users: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/user/{uid}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<UserResponseDto>> fetchUser(@PathVariable String uid) {
        ModelMapper modelMapper = new ModelMapper();
        ApiResponse<UserResponseDto> responseObject = new ApiResponse<>();

        UserResponseDto userResponseDto = new UserResponseDto();
        UserDto userResponse = new UserDto();
        AccountDto accountResponse = new AccountDto();

        try {
            //Get data
            User user = usersController.getUser(uid);
            Account account = usersController.getUserAccounts(user.getId());

            //Map data to DTO
            if ( user != null ){
                userResponse = modelMapper.map(user, UserDto.class);
            }

            if ( account != null ){
                accountResponse = modelMapper.map(account, AccountDto.class);
            }
            //Build response
            userResponseDto.setUser(userResponse);
            userResponseDto.setAccount(accountResponse);

            if( ObjectUtils.isEmpty(userResponse) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(userResponseDto);
            responseObject.setMessage("User fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching users: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PostMapping(value ="/user/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        ApiResponse<User> responseObject = new ApiResponse<>();
        logger.debug(user.toString());
        try {

            User userCreated = usersController.createUser(user);

            if( ObjectUtils.isEmpty(userCreated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("User could not be created.");
            }else{
                responseObject.setCode("200");
                responseObject.setPayload(userCreated);
                responseObject.setMessage("User created successfully");
            }

            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while creating user: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }


    @PutMapping(value ="/user/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        ApiResponse<User> responseObject = new ApiResponse<>();
        try {
            User userUpdated = usersController.modifyUser(id, user, "UPDATE");

            if( ObjectUtils.isEmpty(userUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(userUpdated);
            responseObject.setMessage("User updated successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while updating user: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @PutMapping(value ="/user/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id, @RequestBody User user) {
        ApiResponse<User> responseObject = new ApiResponse<>();
        try {
            User userUpdated = usersController.modifyUser(id, user, "DELETE");

            if( ObjectUtils.isEmpty(userUpdated) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(userUpdated);
            responseObject.setMessage("User deleted successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while deleting user: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/user/{id}/accounts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<Account>> getUserAccounts(@PathVariable Long id) {
        ApiResponse<Account> responseObject = new ApiResponse<>();
        try {
            Account account = usersController.getUserAccounts(id);

            if( ObjectUtils.isEmpty(account) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(account);
            responseObject.setMessage("User account fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching account: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/user/{id}/myorganizations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<Organization>>> getMyOrganizations(@PathVariable Long id) {
        ApiResponse<List<Organization>> responseObject = new ApiResponse<>();
        try {
            List<Organization> organizations = usersController.getMyOrganizations(id);

            if( ObjectUtils.isEmpty(organizations) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizations);
            responseObject.setMessage("User account fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching organizations: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/user/{uid}/organizations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<List<Organization>>> getUserOrganizations(@PathVariable String uid) {
        ApiResponse<List<Organization>> responseObject = new ApiResponse<>();
        try {
            List<Organization> organizations = usersController.getUserOrganization(uid);

            if( ObjectUtils.isEmpty(organizations) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(organizations);
            responseObject.setMessage("User organizations fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching organizations: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }






    /*
    @GetMapping(value ="/user/{id}/payments", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<User>> fetchUser(@PathVariable Long id) {
        ApiResponse<User> responseObject = new ApiResponse<>();
        try {
            User user = usersController.getUser(id);

            if( ObjectUtils.isEmpty(user) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(user);
            responseObject.setMessage("User fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching users: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    }

    @GetMapping(value ="/user/{id}/transactions", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<User>> fetchUser(@PathVariable Long id) {
        ApiResponse<User> responseObject = new ApiResponse<>();
        try {
            User user = usersController.getUser(id);

            if( ObjectUtils.isEmpty(user) ) {
                responseObject.setCode("400");
                responseObject.setMessage("No response from users service");
            }
            responseObject.setCode("200");
            responseObject.setPayload(user);
            responseObject.setMessage("User fetched successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch(Exception e) {
            logger.error("Exception while fetching users: {}", e.toString());
            responseObject.setMessage(e.getMessage());
            responseObject.setCode("500");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }
    } */
}
