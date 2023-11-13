package com.fciencias.freshbowl.controllers.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.freshbowl.models.User;
import com.fciencias.freshbowl.services.users.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UsersApi {

    private final UserRepository userRepository;

    @Autowired
    public UsersApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers()
    {
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User createdUser = null;
        if(user!= null)
            createdUser = userRepository.save(user);
        else
            return ResponseEntity.badRequest().build();

        return ResponseEntity.created(URI.create("/api/users/" + createdUser.getUserId())).build();
        
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {
        if(userRepository.findById(user.getUserId()).orElse(null) != null)
           return ResponseEntity.ok(userRepository.save(user));
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId)
    {
        if(userRepository.findById(userId).isEmpty())
            return ResponseEntity.notFound().build();
            
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
    
}
