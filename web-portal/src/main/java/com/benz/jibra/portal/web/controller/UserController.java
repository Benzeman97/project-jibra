package com.benz.jibra.portal.web.controller;

import com.benz.jibra.portal.web.payload.request.LoginRequest;
import com.benz.jibra.portal.web.payload.request.SignUpRequest;
import com.benz.jibra.portal.web.payload.response.UserResponse;
import com.benz.jibra.portal.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    private static Logger LOG= LogManager.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService=userService;
    }


    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> registerUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
        if (!signUpRequest.getNicOrPassport().trim().isEmpty() && !signUpRequest.getFirstName().trim().isEmpty()
                && !signUpRequest.getEmail().trim().isEmpty() && !signUpRequest.getPassword().trim().isEmpty()) {

            LOG.info(String.format("user is saved and return response with status %s",HttpStatus.OK.getReasonPhrase()));
                      return new ResponseEntity<>(userService.signUpUser(signUpRequest), HttpStatus.OK);
        }else {
            LOG.info("user details are required");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<UserResponse> loginUser(@RequestBody LoginRequest loginRequest) throws Exception
   {
       if(!loginRequest.getUserName().trim().isEmpty() && !loginRequest.getPassword().trim().isEmpty()) {
           LOG.info(String.format("user is logged and return response with status %s",HttpStatus.OK.getReasonPhrase()));
           return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginRequest));
       }
       else {
           LOG.error("username and password required");
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

   }
}
