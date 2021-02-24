package com.benz.security.web.api.dao;

import com.benz.security.web.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role,Integer> {
}
