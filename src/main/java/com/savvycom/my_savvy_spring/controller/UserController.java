package com.savvycom.my_savvy_spring.controller;

import com.savvycom.my_savvy_spring.dto.request.CreateUserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping(headers = "api-version=1")
    public String createUser(@RequestBody CreateUserDTO createUserDTO) {
        return "Create user successfully with username: " + createUserDTO.getUserName();
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestBody CreateUserDTO createUserDTO) {
        return String.format("Update user successfully with id: %s, username: %s", id, createUserDTO.getUserName());
    }

    @PatchMapping("/{userId}")
    public String updateStatusUser(@PathVariable("userId") String id, @RequestParam(required = false) boolean status) {
        return String.format("Update user successfully with id: %s, username: %s", id, status);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "Delete user successfully with id: " + userId;
    }

    @GetMapping
    public String getListUser(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return "Get list userssssss with page: " + page + " and size: " + size;
    }

    @GetMapping("/{userId}")
    public String getUserById(@PathVariable String userId) {
        return "Get user by id: " + userId;
    }

}