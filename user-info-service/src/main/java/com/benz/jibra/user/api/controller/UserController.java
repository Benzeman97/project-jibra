package com.benz.jibra.user.api.controller;

import com.benz.jibra.user.api.entity.User;
import com.benz.jibra.user.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
   public UserController(UserService userService)
   {
       this.userService=userService;
   }

   @GetMapping(value = "/one",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<User> getUser(@RequestBody User user)
   {

        if(user.getUserId()!=0 && !user.getNicOrPassport().trim().isEmpty())
            return new ResponseEntity<>(userService.getUser(user), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

   }

   @PostMapping(value = "/save",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<User> saveUser(@RequestBody User user)
   {

       if(!user.getNicOrPassport().trim().isEmpty() && !user.getEmail().trim().isEmpty() &&
         !user.getPassword().trim().isEmpty() && !user.getFirstName().trim().isEmpty())
           return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
       else
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }
}
