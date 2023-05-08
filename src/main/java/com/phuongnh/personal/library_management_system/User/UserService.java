package com.phuongnh.personal.library_management_system.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(UUID id, User user) {
        User existingUser = getUserById(id);
        existingUser.setGivenName(user.getGivenName());
        existingUser.setSurname(user.getSurname());
        existingUser.setGivenSurname(user.isGivenSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setImgsrc(user.getImgsrc());
        existingUser.setBanned(user.isBanned());
        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        userRepository.delete(getUserById(id));
    }
}
