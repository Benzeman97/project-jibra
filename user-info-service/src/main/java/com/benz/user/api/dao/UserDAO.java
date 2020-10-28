package com.benz.user.api.dao;

import com.benz.user.api.entity.User;
import com.benz.user.api.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, UserIdentity> {

    @Query("from User where userId = :id and nicOrPassport = :u_id")
    Optional<User> findUser(@Param("id") long userId,@Param("u_id") String uniqueId);
}
