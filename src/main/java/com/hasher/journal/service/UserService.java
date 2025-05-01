package com.hasher.journal.service;

import com.hasher.journal.entity.User;
import com.hasher.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // saveEntry
    public void saveUser(User user){
        userRepository.save(user);
    }

    // getAll
    public List<User> getALl(){
        return userRepository.findAll();
    }

    // findById
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    // deleteById
    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    // findByUserName

    public User findByUser(String user) {
        return userRepository.findByUserName(user);
    }
}
