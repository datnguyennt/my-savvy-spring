package com.savvycom.my_savvy_spring.service;

import com.savvycom.my_savvy_spring.model.User;
import com.savvycom.my_savvy_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean isExistUserByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public User updateUser(String id, User userUpdate) {
        User user = getUserById(id);
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setBirthday(userUpdate.getBirthday());
        user.setAddress(userUpdate.getAddress());
        user.setPhone(userUpdate.getPhone());
        user.setAvatar(userUpdate.getAvatar());
        user.setRole(userUpdate.getRole());
        user.setStatus(userUpdate.isStatus());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
