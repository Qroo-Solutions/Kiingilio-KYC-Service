package com.qroo.kyc.services;

import com.qroo.kyc.data.dao.OrganizationsRepository;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.dao.filters.SearchSpecification;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationsService {
    @Autowired
    OrganizationsRepository repository;
    public List<Organization> getAllOrganizations(){
        return repository.findAll();
    }
    public Page<Organization> searchOrganizations(SearchRequest request) {
        SearchSpecification<Organization> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return repository.findAll(specification, pageable);
    }

    public Organization getById(Long id){
        return repository.findById(id).get();
    }
    public Organization getByAlias(String alias){
        return repository.findByAlias(alias);
    }
    public List<Organization> getByUser(User user){
        return repository.findByUser(user);
    }

    public Organization createOrganization( Organization organization){
        return repository.save(organization);
    }

    public Organization updateOrganization( Long id, Organization organization){
        if (repository.existsById(id)) {
            organization.setId(id);
            repository.save(organization);
        }
        return repository.findById(id).get();
    }

    public List<Organization> getByOrganizationOwner(User user){
        return repository.findByUser(user);
    }
}