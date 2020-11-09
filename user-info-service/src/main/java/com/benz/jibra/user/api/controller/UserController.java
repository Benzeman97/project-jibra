package com.benz.jibra.user.api.controller;

import com.benz.jibra.user.api.entity.User;
import com.benz.jibra.user.api.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final private static Logger LOG = LogManager.getLogger(UserController.class);

    private UserService userService;

   @Autowired
   public UserController(UserService userService)
   {
       this.userService=userService;
   }

   @GetMapping(value = "/one",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<User> getUser(@RequestBody User user)
   {

        if(user.getUserId()!=0 && !user.getNicOrPassport().trim().isEmpty()) {
            LOG.info(String.format("userId %d and uniqueId %s",user.getUserId(),user.getNicOrPassport()));
            return new ResponseEntity<>(userService.getUser(user), HttpStatus.OK);
        }
        else {
            LOG.warn(String.format("userId %d and uniqueId %s",user.getUserId(),user.getNicOrPassport()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

   }

   @PostMapping(value = "/save",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<User> saveUser(@RequestBody User user)
   {

       if(!user.getNicOrPassport().trim().isEmpty() && !user.getEmail().trim().isEmpty() &&
         !user.getPassword().trim().isEmpty() && !user.getFirstName().trim().isEmpty()) {
           LOG.info(String.format("uniqueId %s, email %s, password %s, firstName %s",user.getNicOrPassport(),user.getEmail(),user.getPassword()));
           return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
       }
       else {
           LOG.warn(String.format("uniqueId %s, email %s, password %s, firstName %s",user.getNicOrPassport(),user.getEmail(),user.getPassword()));
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
}
