package com.nextgenqa.controller;

import com.nextgenqa.model.User;
import com.nextgenqa.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar usuários.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
