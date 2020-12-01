package com.benz.jibra.portal.web.service.impl;

import com.benz.jibra.portal.web.dao.UserDAO;
import com.benz.jibra.portal.web.entity.User;
import com.benz.jibra.portal.web.entity.UserStatus;
import com.benz.jibra.portal.web.exception.DataNotFoundException;
import com.benz.jibra.portal.web.exception.UserExistedException;
import com.benz.jibra.portal.web.payload.request.LoginRequest;
import com.benz.jibra.portal.web.payload.request.SignUpRequest;
import com.benz.jibra.portal.web.payload.response.UserResponse;
import com.benz.jibra.portal.web.security.Oauth2AuthenticationProvider;
import com.benz.jibra.portal.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    final private static Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;
    private Oauth2AuthenticationProvider authenticationProvider;

    @Autowired
    public UserServiceImpl(UserDAO userDAO,Oauth2AuthenticationProvider authenticationProvider) {
        this.userDAO = userDAO;
        this.authenticationProvider=authenticationProvider;
    }

    @Override
    public UserResponse signUpUser(SignUpRequest signUpRequest) throws Exception {

        User e_user=userDAO.findUserByNicOrPassport(signUpRequest.getNicOrPassport())
                .orElse(null);

        if (e_user != null) {
            LOG.warn(String.format("user is existed with %s", signUpRequest.getNicOrPassport()));
            throw new UserExistedException(String.format("user is existed with %s", signUpRequest.getNicOrPassport()));
        } else {

            try {
                String password = "{bcrypt}";

                password = password.concat(BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt(12)));

                User user = new User();
                user.setNicOrPassport(signUpRequest.getNicOrPassport());
                user.setFirstName(signUpRequest.getFirstName());
                user.setLastName(signUpRequest.getLastName());
                user.setCountry(signUpRequest.getCountry());
                user.setDob(signUpRequest.getDob());
                user.setEmail(signUpRequest.getEmail());
                user.setTeleNo(signUpRequest.getTeleNo());
                user.setPassword(password);
                user.setRegisteredDate(new Date());
                user.setModifiedDate(new Date());

                UserStatus userStatus =new UserStatus();
                userStatus.setActive(1L);
                userStatus.setAccNonExpired(1L);
                userStatus.setCredentialNonExpired(1L);
                userStatus.setAccNonLocked(1L);

                user.setUserStatus(userStatus);

                userDAO.save(user);
                LOG.info(String.format("user has been saved with %s", user.getNicOrPassport()));

                LOG.info("user saved and UserResponse has returned");
                return new UserResponse(user.getEmail(),getAccessToken(user.getEmail(),signUpRequest.getPassword()));

            }catch (Exception ex)
            {
                LOG.error("error has occurred during the registration");
                throw new Exception();
            }

        }

    }

    @Override
    public UserResponse loginUser(LoginRequest loginRequest) throws Exception{

         User user = userDAO.findUserByEmail(loginRequest.getUserName())
                 .orElseThrow(()->new DataNotFoundException(String.format("user is not found with %s",loginRequest.getUserName())));

         String prefix= "{bcrypt}";

         String hash_pw = user.getPassword().substring(prefix.length());

         if(user.getEmail().equals(loginRequest.getUserName()) && BCrypt.checkpw(loginRequest.getPassword(),hash_pw)) {
             LOG.info("user logged and UserResponse has returned");
             return new UserResponse(user.getEmail(), getAccessToken(user.getEmail(), loginRequest.getPassword()));
         }
         else {
             LOG.info("invalid username or password provided during the login");
             throw new BadCredentialsException("invalid username or password");
         }
    }

    public String getAccessToken(String username,String password)
    {
        LOG.info("jwt (access_token) token is returned");
        return authenticationProvider.obtainToken(username,password).toString();
    }
}
