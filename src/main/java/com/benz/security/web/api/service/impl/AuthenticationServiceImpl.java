package com.benz.security.web.api.service.impl;

import com.benz.security.web.api.dao.RoleDAO;
import com.benz.security.web.api.dao.UserDAO;
import com.benz.security.web.api.entity.Permission;
import com.benz.security.web.api.entity.Role;
import com.benz.security.web.api.entity.User;
import com.benz.security.web.api.exception.DataNotFoundException;
import com.benz.security.web.api.exception.UserIsExistedException;
import com.benz.security.web.api.payload.request.LoginRequest;
import com.benz.security.web.api.payload.request.SignupRequest;
import com.benz.security.web.api.payload.response.Response;
import com.benz.security.web.api.security.OAuth2AuthenticationProvider;
import com.benz.security.web.api.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
   private OAuth2AuthenticationProvider authenticationProvider;

    private static final Logger LOGGER= LogManager.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(UserDAO userDAO, OAuth2AuthenticationProvider authenticationProvider,RoleDAO roleDAO)
    {
        this.userDAO=userDAO;
        this.authenticationProvider=authenticationProvider;
        this.roleDAO=roleDAO;
    }

    @Override
    public Response userLogin(LoginRequest login) {

        User user= userDAO.findUserByEmail(login.getUserName()).orElse(null);

           if(Objects.isNull(user))
             throw new DataNotFoundException(String.format("User is not found with %s",login.getUserName()));

           String prefix="{bcrypt}";

           String hashPwd =  user.getPassword().substring(prefix.length());

           if(login.getUserName().equals(user.getEmail()) && BCrypt.checkpw(login.getPassword(),hashPwd)) {
               LOGGER.info("Login success");
               return new Response(user.getEmail(), authenticationProvider.obtainToken(user.getEmail(), login.getPassword()).toString());
           }
           LOGGER.error("Invalid Credentials");
           throw new BadCredentialsException("Invalid Credentials");
    }

    @Override
    public Response userRegistration(SignupRequest signup) {


        if(!Objects.isNull(userDAO.findUserByEmail(signup.getEmail()).orElse(null)))
            throw new UserIsExistedException(String.format("User is existed with %s",signup.getEmail()));

       try {
            User user=new User();

            String password="{bcrypt}";

           password = password.concat(BCrypt.hashpw(signup.getPassword(),BCrypt.gensalt(12)));


            user.setUserName(signup.getUserName());
            user.setEmail(signup.getEmail());
            user.setPassword(password);
            user.setActive("y");
            user.setAccNonExpired("y");
            user.setCredentialNonExpired("y");
            user.setAccNonLocked("y");
            user.setRoles(signup.getRoles());


           Set<Role> roles = new HashSet<>();

            user.getRoles().forEach(role->{
                 roles.add(role);
              //  Set<Permission> permissions=new HashSet<>();

                /*role.getPermissions().forEach(perm->{
                    permissions.add(perm);
                });

                role.setPermissions(permissions);*/

            });

           user.setRoles(roles);

           userDAO.save(user);


            LOGGER.info("user is saved and response is returned successfully");
           return new Response(user.getEmail(), authenticationProvider.obtainToken(user.getEmail(), signup.getPassword()).toString());

        }catch (NumberFormatException ex){
            LOGGER.error("NumberFormat Exception");
            throw new NumberFormatException("NumberFormat Exception");
        }
      /*  catch (Exception ex){
            LOGGER.error("invalid username or password");
           throw new BadCredentialsException("invalid username or password",ex);
        }*/
    }
}
