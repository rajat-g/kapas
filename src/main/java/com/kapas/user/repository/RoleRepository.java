package com.kapas.user.repository;

import com.kapas.user.entity.Role;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends BaseJpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

}
