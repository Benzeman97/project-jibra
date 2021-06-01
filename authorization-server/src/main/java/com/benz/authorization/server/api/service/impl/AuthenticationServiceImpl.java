package com.benz.authorization.server.api.service.impl;

import com.benz.authorization.server.api.dao.UserDAO;
import com.benz.authorization.server.api.db.EPermission;
import com.benz.authorization.server.api.entity.User;
import com.benz.authorization.server.api.entity.UserStatus;
import com.benz.authorization.server.api.exception.DataNotFoundException;
import com.benz.authorization.server.api.exception.UserExistedException;
import com.benz.authorization.server.api.payload.request.LogInRequest;
import com.benz.authorization.server.api.payload.request.SignUpRequest;
import com.benz.authorization.server.api.payload.response.LogInResponse;
import com.benz.authorization.server.api.payload.response.SignUpResponse;
import com.benz.authorization.server.api.security.OAuth2AuthenticationProvider;
import com.benz.authorization.server.api.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    final private static Logger LOGGER = LogManager.getLogger(AuthenticationServiceImpl.class);

    private UserDAO userDAO;
    private OAuth2AuthenticationProvider authProvider;

    public AuthenticationServiceImpl(UserDAO userDAO, OAuth2AuthenticationProvider authProvider) {
        this.userDAO = userDAO;
        this.authProvider = authProvider;
    }

    @Override
    public SignUpResponse userSignUp(SignUpRequest signUpRequest) throws Exception {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setCountry(signUpRequest.getCountry());
        user.setTeleNo(signUpRequest.getTeleNo());
        user.setNicOrPassport(signUpRequest.getNicOrPassport());
        user.setRegisteredDate(new Date());
        user.setModifiedDate(new Date());

        UserStatus userStatus = new UserStatus();
        userStatus.setActive(1);
        userStatus.setAccNonExpired(1);
        userStatus.setAccNonLocked(1);
        userStatus.setCredentialNonExpired(1);

        user.setUserStatus(userStatus);
        user.setRoles(signUpRequest.getRoles());

        User e_user = userDAO.findByEmail(signUpRequest.getEmail()).orElse(null);

        if (Objects.nonNull(e_user)) {
            LOGGER.error(String.format("User is existed with %s", e_user.getEmail()));
            throw new UserExistedException(String.format("User is existed with %s", e_user.getEmail()));
        }

        Date dob = new SimpleDateFormat("dd-MMM-yyyy").parse(getDOb(signUpRequest.getDob()));
        user.setDob(dob);

        String passwordPrefix = "{bcrypt}";
        passwordPrefix = passwordPrefix.concat(BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt(12)));
        user.setPassword(passwordPrefix);
        LOGGER.info(String.format("user has been registered with %s and %s", user.getNicOrPassport(), user.getEmail()));
        userDAO.save(user);
        return new SignUpResponse(user.getEmail(), authProvider.obtainToken(user.getEmail(), signUpRequest.getPassword()).getValue(), userStatus.getActive() == 1);
    }

    @Override
    public LogInResponse userLogIn(LogInRequest logInRequest) throws Exception {
        String username = logInRequest.getUsername();
        String password = logInRequest.getPassword();

        User user = userDAO.findByEmail(username).orElse(null);

        if (Objects.isNull(user))
            throw new DataNotFoundException(String.format("user is not found with %s", username));

        String prefix = "{bcrypt}";
        String hashPwd = user.getPassword().substring(prefix.length());


        if (user.getEmail().equals(username) && BCrypt.checkpw(password, hashPwd)) {
            OAuth2AccessToken accessToken = authProvider.obtainToken(username, password);
            boolean isActive = accessToken.isExpired();
            if (isActive)
                throw new BadCredentialsException(String.format("Token is expired with %s", username));

            LOGGER.info(String.format("user is logged with %s", username));
            return new LogInResponse(user.getEmail(), accessToken.getValue(), !isActive);
        }
        LOGGER.error("username or password is incorrect");
        throw new BadCredentialsException("username or password is incorrect");
    }

    private String getDOb(String dob)
    {
        String month = dob.substring(4,7);
        String date = dob.substring(8,10);
        String year = dob.substring(11);

        return date.concat("-").concat(month).concat("-").concat(year);

    }
}
