package com.hasher.journal.controller;

import com.hasher.journal.entity.User;
import com.hasher.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // getAllUser
    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> all = userService.getALl();

        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // createAdmin
    @PostMapping("/create-new-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User newAdmin){
        User existingUser = userService.findByUserName(newAdmin.getUserName());

        if (existingUser != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        userService.saveNewUser(newAdmin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

}
