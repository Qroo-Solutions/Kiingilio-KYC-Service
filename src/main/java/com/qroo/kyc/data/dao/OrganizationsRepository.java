package com.qroo.kyc.data.dao;

import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationsRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    List<Organization> findByUser(User user);
    Organization findByAlias(String alias);
    //List<Organization> findByUid(String uid);
}