package com.hasher.journal.controller;

import com.hasher.journal.entity.User;
import com.hasher.journal.service.UserService;
import org.aspectj.bridge.MessageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // addUser
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        Optional<User> userOptional = userService.findById(newUser.getId());

        if(userOptional.isEmpty()){
            userService.saveUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Username already present, Use different username.",HttpStatus.BAD_REQUEST);
    }

    // getAll
    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<User> userList = userService.getALl();
        if(!userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // getById




    // updateUser
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User old = userService.findByUser(userName);

        if(old != null){
            old.setUserName(user.getUserName());
            old.setPassword(user.getPassword());
            userService.saveUser(old);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // deleteUser

}
