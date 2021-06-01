package com.benz.authorization.server.api.service.impl;

import com.benz.authorization.server.api.dao.UserDAO;
import com.benz.authorization.server.api.entity.User;
import com.benz.authorization.server.api.exception.DataNotFoundException;
import com.benz.authorization.server.api.model.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByEmail(username)
                .orElseThrow(() -> new DataNotFoundException(String.format("data not found with %s", username)));

        return AuthUserDetails.builder(user);
    }
}
