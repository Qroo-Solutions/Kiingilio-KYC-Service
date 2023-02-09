package com.qroo.kyc.data.dao;

import com.qroo.kyc.data.vo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}