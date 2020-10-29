package com.benz.user.api.service;

import com.benz.user.api.entity.User;

import java.util.List;

public interface UserService {

    User getUser(User user);
    List<User> getUsers();
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
}
