package com.benz.security.web.api.controller;

import com.benz.security.web.api.dao.UserDAO;
import com.benz.security.web.api.payload.request.LoginRequest;
import com.benz.security.web.api.payload.request.SignupRequest;
import com.benz.security.web.api.payload.response.Response;
import com.benz.security.web.api.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600L)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOGGER= LogManager.getLogger(AuthenticationController.class);

    private AuthenticationService authenticationService;
    private UserDAO userDAO;

    public AuthenticationController(AuthenticationService authenticationService,UserDAO userDAO)
    {
        this.authenticationService=authenticationService;
        this.userDAO=userDAO;
    }

    @PostMapping(value = "/login",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response> userLogin(@RequestBody LoginRequest loginRequest)
    {
         if(!loginRequest.getUserName().trim().isEmpty() && !loginRequest.getPassword().trim().isEmpty()) {
             LOGGER.info(String.format("user is logged and return response with status %s",HttpStatus.OK.getReasonPhrase()));
             return new ResponseEntity<>(authenticationService.userLogin(loginRequest), HttpStatus.OK);
         }
         LOGGER.error("username and password are required");
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/signup",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response> userRegistration(@RequestBody SignupRequest signup)
    {
          if(!signup.getUserName().trim().isEmpty() && !signup.getPassword().trim().isEmpty() && !signup.getEmail().trim().isEmpty()) {
             LOGGER.info(String.format("user is saved and return response with status %s",HttpStatus.CREATED.getReasonPhrase()));
            return new ResponseEntity<>(authenticationService.userRegistration(signup),HttpStatus.CREATED);
          }
          LOGGER.error("username,email and password are required");
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public String demo()
    {
        System.out.println("Hallo,ich hisBe Nafaz");
        return "Hello Benzema";
    }


}
