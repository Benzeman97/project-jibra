package com.benz.security.web.api.service;

import com.benz.security.web.api.dao.UserDAO;
import com.benz.security.web.api.exception.DataNotFoundException;
import com.benz.security.web.api.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        return userDAO.findUserByEmail(name).map(u -> CustomUserDetails.builder(u))
                .orElseThrow(()->new DataNotFoundException(String.format("User is not found with %s",name)));
    }
}
