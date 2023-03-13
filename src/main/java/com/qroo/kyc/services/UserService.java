package com.qroo.kyc.services;

import com.qroo.kyc.data.dao.UsersRepository;
import com.qroo.kyc.data.dao.filters.SearchRequest;
import com.qroo.kyc.data.dao.filters.SearchSpecification;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

//CRUD operations
@Service
public class UserService{
    @Autowired
    UsersRepository repository;
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public Page<User> searchUsers(SearchRequest request) {
        SearchSpecification<User> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return repository.findAll(specification, pageable);
    }


    public User getById(Long id){
        return repository.findById(id).get();
    }

    public User getByUid(String uid){
        return repository.findByUid(uid);
    }

    public User createUser(User user){
        return repository.save(user);
    }

    public User updateUser( Long id, User user){
        if (repository.existsById(id)) {
            user.setId(id);
            repository.save(user);
        }
        return repository.findById(id).get();
    }
}