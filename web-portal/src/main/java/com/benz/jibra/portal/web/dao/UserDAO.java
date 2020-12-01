package com.benz.jibra.portal.web.dao;

import com.benz.jibra.portal.web.entity.User;
import com.benz.jibra.portal.web.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, UserIdentity> {

    @Query("from User where nicOrPassport = :uId")
    Optional<User> findUserByNicOrPassport(@Param("uId") String uniqueId);

    Optional<User> findUserByEmail(String email);
}
