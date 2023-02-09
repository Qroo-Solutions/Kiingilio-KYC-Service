package com.qroo.kyc.controllers;

import com.qroo.common.data.constants.Status;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.vo.Role;
import com.qroo.kyc.services.RolesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RolesController{
    @Autowired
    RolesService service;
    private final Logger logger;

    public RolesController() {
        logger = LoggerFactory.getLogger(RolesController.class);
    }

    public List<Role> getAllRoles(){
        return service.getAllRoles();
    }

    public Page<Role> searchRoles(SearchRequest request){
        return service.searchRoles(request);
    }

    public Role getRole(Long id){
        return service.getById(id);
    }

    public Role createRole(Role role){
        return service.createRole(role);
    }

    public Role modifyRole(Long id, Role role){
        return service.updateRole(id, role);
    }

    public Role modifyRole(Long id, Status status){
        Role roleToDelete = service.getById(id);
        if ( roleToDelete != null){
            roleToDelete.setStatus(status);
        }
        return service.updateRole(id, roleToDelete);
    }
}