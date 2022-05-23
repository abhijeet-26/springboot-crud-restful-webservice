package com.springbootcrudrestful.controllers;

import com.springbootcrudrestful.entities.User;
import com.springbootcrudrestful.exception.ResourceNotFoundException;
import com.springbootcrudrestful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping
    public List<User> getAllUser()
    {
        return this.userRepository.findAll();
    }

    //get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id")long userId){
        return this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    //create user
    @PostMapping
    public User createUser(@RequestBody User user)
    {
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
        User exisiting = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        exisiting.setFirstName(user.getFirstName());
        exisiting.setLastName(user.getLastName());
        exisiting.setEmail(user.getEmail());
        return this.userRepository.save(exisiting);
    }

    //delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long userId)
    {
        User exisiting = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        this.userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }

}
