package com.hasher.journal.controller;

import com.hasher.journal.api.response.WhetherResponse;
import com.hasher.journal.entity.User;
import com.hasher.journal.service.UserService;
import com.hasher.journal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    // getUser
    @GetMapping
    public ResponseEntity<?> getUser(){
        List<User> userList = userService.getALl();
        WhetherResponse whetherResponse = weatherService.getWhether("Pune");

        if(!userList.isEmpty()) {
            if(whetherResponse != null){
                return new ResponseEntity<>(userList + " "+ whetherResponse,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(userList,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // updateUser
    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);

        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userService.saveNewUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // deleteUser
    @DeleteMapping
    public ResponseEntity<User> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);

        if(userInDb != null){
            userService.deleteById(userInDb.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
