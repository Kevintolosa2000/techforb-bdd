package com.techforb.techforb_bdd.controllers;

import com.techforb.techforb_bdd.models.User;
import com.techforb.techforb_bdd.security.JwtUtil;
import com.techforb.techforb_bdd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/login")
    public ResponseEntity<?> setUser(@RequestBody User user) {

        boolean isAuthenticated = userService.authenticateUser(user.getEmail(), user.getPassword());

        if (isAuthenticated) {
            User loggedInUser = userService.findByEmail(user.getEmail()).get();
            loggedInUser.setPassword(null);
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Optional<User> existingUserByEmail = userService.findByEmail(user.getEmail());
        Optional<User> existingUserByUsername = userService.findByUsername(user.getUsername());

        if (existingUserByUsername.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "* El nombre de usuario ya está registrado"));
        }
        if (existingUserByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "* El correo ya está registrado"));
        }

        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado exitosamente"));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get-an-user/{id}")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        return this.userService.getById(id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") String id) {
        boolean deleted = this.userService.deleteUser(id);
        if (deleted) {
            return "user was deleted";
        } else {
            return "ERROR: user was not deleted";
        }
    }
}
