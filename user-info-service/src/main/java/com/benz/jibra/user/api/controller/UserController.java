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

import java.util.List;

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
            LOG.info("get user is called");
            return new ResponseEntity<>(userService.getUser(user), HttpStatus.OK);
        }
        else {
            LOG.warn("userId and nic or passport are required");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

   }

   @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<List<User>> getUsers()
   {
       LOG.info("get all the users is called");
       return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
   }

   @PostMapping(value = "/save",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<User> saveUser(@RequestBody User user)
   {

       if(!user.getNicOrPassport().trim().isEmpty() && !user.getEmail().trim().isEmpty() &&
         !user.getPassword().trim().isEmpty() && !user.getFirstName().trim().isEmpty()) {
           LOG.info("user save is called");
           return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
       }
       else {
           LOG.warn("nic or passport and email and password and firstname are required");
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }

   @PutMapping(value = "/update",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<User> updateUser(@RequestBody User user)
   {
        if(!user.getNicOrPassport().trim().isEmpty())
        {
            LOG.info("user update is called");
            return ResponseEntity.ok(userService.updateUser(user));
        }else
        {
            LOG.warn("nic or passport is required");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
   }

   @DeleteMapping(value = "/delete",consumes = {MediaType.APPLICATION_JSON_VALUE})
   public void deleteUser(@RequestBody User user)
   {
          if(user.getUserId()!=0 && !user.getNicOrPassport().trim().isEmpty())
          {
              LOG.info("user delete is called");
              userService.deleteUser(user);
          }else{
              LOG.info("userId and nic or passport are required");
              throw new IllegalArgumentException("userId and nic or passport are required");
          }
   }
}
