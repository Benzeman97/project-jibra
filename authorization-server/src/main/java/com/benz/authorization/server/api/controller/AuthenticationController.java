package com.benz.authorization.server.api.controller;

import com.benz.authorization.server.api.payload.request.LogInRequest;
import com.benz.authorization.server.api.payload.request.SignUpRequest;
import com.benz.authorization.server.api.payload.response.LogInResponse;
import com.benz.authorization.server.api.payload.response.SignUpResponse;
import com.benz.authorization.server.api.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge=3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/signup", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignUpResponse> userSignUp(@RequestBody SignUpRequest request) throws Exception {
        return (request.getFirstName().trim().isEmpty() && request.getEmail().trim().isEmpty() && request.getPassword().trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(authenticationService.userSignUp(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LogInResponse> userLogIn(@RequestBody LogInRequest request) throws Exception {
        return (request.getUsername().trim().isEmpty() && request.getPassword().trim().isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(authenticationService.userLogIn(request));
    }
	
}
