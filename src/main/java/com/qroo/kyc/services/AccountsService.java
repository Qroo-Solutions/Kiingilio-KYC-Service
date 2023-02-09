package com.qroo.kyc.services;

import com.qroo.kyc.data.dao.AccountsRepository;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//CRUD operations
@Service
public class AccountsService {
    @Autowired
    AccountsRepository repository;
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public Account getById(Long id){
        return repository.findById(id).get();
    }

   public Account getByUser(User user){
        return repository.findByUser(user);
    }
    public List<Account> getByOrganization(Organization organization){
        return repository.findByOrganization(organization);
    }

    public Account createAccount( Account account){
        return repository.save(account);
    }

    public Account updateAccount( Long id, Account account){
        if (repository.existsById(id)) {
            account.setId(id);
            repository.save(account);
        }
        return repository.findById(id).get();
    }
}