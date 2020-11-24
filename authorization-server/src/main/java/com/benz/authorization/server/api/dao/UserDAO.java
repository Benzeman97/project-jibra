package com.benz.authorization.server.api.dao;

import com.benz.authorization.server.api.entity.User;
import com.benz.authorization.server.api.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, UserIdentity> {

    Optional<User> findByEmail(String email);

    @Query("from User where userId= :userId and nicOrPassport= :nicOrPassport")
    Optional<User> existsByUserIdAndNicOrPassport(@Param("userId") long userId, @Param("nicOrPassport") String nicOrPassport);
}
