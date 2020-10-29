package com.benz.user.api.dao;

import com.benz.user.api.entity.User;
import com.benz.user.api.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, UserIdentity> {


    Optional<User> findUserByUserIdAndAndNicOrPassport(UserIdentity userIdentity);

    @Query("from User")
    Optional<List<User>> findAllUsers();

    boolean existsUserByUserIdAndNicOrPassport(UserIdentity userIdentity);

}
