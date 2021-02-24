package com.benz.security.web.api.dao;

import com.benz.security.web.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    Optional<User> findUserByEmail(String mail);
}
