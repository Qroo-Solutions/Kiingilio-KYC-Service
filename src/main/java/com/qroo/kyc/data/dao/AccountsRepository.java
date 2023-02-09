package com.qroo.kyc.data.dao;

import com.qroo.common.data.constants.Status;
import com.qroo.kyc.data.vo.Account;
import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.Role;
import com.qroo.kyc.data.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Role> {
    Account findByUser(User user);
    List<Account> findByOrganization(Organization organization);
    List<Account> findByStatus(Status status);
}