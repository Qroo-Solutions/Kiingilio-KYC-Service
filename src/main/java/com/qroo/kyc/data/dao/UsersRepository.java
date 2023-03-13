package com.qroo.kyc.data.dao;

import com.qroo.kyc.data.vo.Organization;
import com.qroo.kyc.data.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUid(String uid);
}