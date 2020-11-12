package com.benz.jibra.user.api.dao;

import com.benz.jibra.user.api.entity.User;
import com.benz.jibra.user.api.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, UserIdentity> {

    @Query("from User order by userId asc")
    Optional<List<User>> findAllUsers();

    @Query("from User where nicOrPassport=:id")
    User existsUserByNicOrPassport(@Param("id") String identity);

    @Query("from User where userId=:user_id and nicOrPassport=:unique_id")
    User existsUserByUserIdAndNicOrPassport(@Param("user_id") long id,@Param("unique_id") String identity);


}
