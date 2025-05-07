package com.hasher.journal.controller;

import com.hasher.journal.entity.User;
import com.hasher.journal.service.JournalEntryService;
import com.hasher.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        User existingUser = userService.findByUserName(newUser.getUserName());

        if (existingUser != null) {
            return new ResponseEntity<>("Username already present, use a different username.", HttpStatus.BAD_REQUEST);
        }

        userService.saveNewUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
