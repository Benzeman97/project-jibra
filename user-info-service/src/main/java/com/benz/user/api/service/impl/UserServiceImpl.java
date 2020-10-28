package com.benz.user.api.service.impl;

import com.benz.user.api.dao.UserDAO;
import com.benz.user.api.entity.User;
import com.benz.user.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public User getUser(User user) {
        return null;
    }
}
