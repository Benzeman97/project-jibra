package com.benz.jibra.user.api.service.impl;

import com.benz.jibra.user.api.dao.UserDAO;
import com.benz.jibra.user.api.entity.User;
import com.benz.jibra.user.api.entity.UserIdentity;
import com.benz.jibra.user.api.exception.DataNotFoundException;
import com.benz.jibra.user.api.exception.UserExistedException;
import com.benz.jibra.user.api.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final private static Logger LOG= LogManager.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public User getUser(User user) {
              LOG.info("get the specific User");
        return userDAO.findById(new UserIdentity(user.getUserId(),user.getNicOrPassport()))
                .orElseThrow(()->new DataNotFoundException(String.format("user is not found with %d and %s",user.getUserId(),user.getNicOrPassport())));
    }

    @Override
    public List<User> getUsers() {
        LOG.info("get all the Users");
       return userDAO.findAllUsers()
               .orElseThrow(()->new DataNotFoundException("no user available in the database"));
    }

    @Override
    public User saveUser(User user){

        boolean exist;

        User n_user=userDAO.existsUserByNicOrPassport(user.getNicOrPassport());

        if(n_user!=null)
            exist=true;
        else
            exist=false;

        if(!exist)
        {
            String password="{bcrypt}";

            password=password.concat(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12)));

            user.setPassword(password);
            user.setRegisteredDate(new Date());
            user.setModifiedDate(new Date());
            LOG.info(String.format("user is saved with %s",user.getNicOrPassport()));
            return userDAO.save(user);
        }
        else {
            LOG.error(String.format("User is existed with %d and %s",n_user.getUserId(),n_user.getNicOrPassport()));
            throw new UserExistedException(String.format("user is existed with %d and %s", n_user.getUserId(),n_user.getNicOrPassport()));
        }
    }


    @Override
    public User updateUser(User user) {

        boolean exist;

        User n_user=userDAO.existsUserByUserIdAndNicOrPassport(user.getUserId(),user.getNicOrPassport());

        if(n_user!=null)
            exist=true;
        else
            exist=false;

        if(exist) {
            user.setRegisteredDate(n_user.getRegisteredDate());
            user.setModifiedDate(new Date());
            LOG.info(String.format("user is updated with %d and %s",user.getUserId(),user.getNicOrPassport()));
            return userDAO.save(user);
        }
        else {
            LOG.error(String.format("user is not found with $d and %s", user.getUserId(),user.getNicOrPassport()));
            throw new DataNotFoundException(String.format("user is not found with %d and %s", user.getUserId(),user.getNicOrPassport()));
        }
    }

    @Override
    public void deleteUser(User user) {

        boolean exist;

        User n_user=userDAO.existsUserByUserIdAndNicOrPassport(user.getUserId(),user.getNicOrPassport());

        if(n_user!=null)
            exist=true;
        else
            exist=false;

        if(exist) {
            LOG.info(String.format("user is deleted with %d and %s",user.getUserId(),user.getNicOrPassport()));
            userDAO.delete(user);
        }
        else {
            LOG.info(String.format("user is not found with %d and %s",user.getUserId(),user.getNicOrPassport()));
            throw new DataNotFoundException(String.format("user is not found with %d and %s",user.getUserId(),user.getNicOrPassport()));
        }
    }
}
