package com.hasher.journal.service;

import com.hasher.journal.entity.User;
import com.hasher.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // saveEntry
    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of ("USER"));
        userRepository.save(user);
    }

    // saveUser
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
    public User findByUserName(String user) {
        return userRepository.findByUserName(user);
    }

    // Create Admin
    public void addNewAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of ("USER", "ADMIN"));
        userRepository.save(user);
    }
}
