package com.qroo.kyc.services;

import com.qroo.kyc.data.dao.RolesRepository;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.dao.filters.SearchSpecification;
import com.qroo.kyc.data.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

//CRUD operations
@Service
public class RolesService{
    @Autowired
    RolesRepository repository;
    public List<Role> getAllRoles(){
        return repository.findAll();
    }
    public Page<Role> searchRoles(SearchRequest request) {
        SearchSpecification<Role> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return repository.findAll(specification, pageable);
    }

    public Role getById(Long id){
        return repository.findById(id).get();
    }

    public Role createRole(Role role){
        return repository.save(role);
    }

    public Role updateRole( Long id, Role role){
        if (repository.existsById(id)) {
            role.setId(id);
            repository.save(role);
        }
        return repository.findById(id).get();
    }
}