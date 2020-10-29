package com.benz.user.api.service.impl;

import com.benz.user.api.dao.UserDAO;
import com.benz.user.api.entity.User;
import com.benz.user.api.entity.UserIdentity;
import com.benz.user.api.exception.DataNotFoundException;
import com.benz.user.api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public User getUser(User user) {

        return userDAO.findUserByUserIdAndAndNicOrPassport(new UserIdentity(user.getUserId(),user.getNicOrPassport()))
                .orElseThrow(()->new DataNotFoundException(String.format("User is not found with %d and %s",user.getUserId(),user.getNicOrPassport())));
    }

    @Override
    public List<User> getUsers() {
         return userDAO.findAllUsers()
                 .orElseThrow(()->new DataNotFoundException("No User available in the database"));
    }

    @Override
    public User saveUser(User user) {

       if(!userDAO.existsUserByUserIdAndNicOrPassport(new UserIdentity(user.getUserId(),user.getNicOrPassport())))
           return userDAO.save(user);
       else
           throw new IllegalArgumentException(String.format("User is existed with %d and %s",user.getUserId(),user.getNicOrPassport()));
    }

    @Override
    public User updateUser(User user) {

        if(userDAO.existsUserByUserIdAndNicOrPassport(new UserIdentity(user.getUserId(),user.getNicOrPassport())))
        return userDAO.save(user);
        else
            throw new DataNotFoundException(String.format("User is not found with %d and %s",user.getUserId(),user.getNicOrPassport()));
    }

    @Override
    public void deleteUser(User user) {

        if(userDAO.existsUserByUserIdAndNicOrPassport(new UserIdentity(user.getUserId(),user.getNicOrPassport())))
            userDAO.delete(user);
        else
            throw new DataNotFoundException(String.format("User is not found with %d and %s",user.getUserId(),user.getNicOrPassport()));


    }


}
