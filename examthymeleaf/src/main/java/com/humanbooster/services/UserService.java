// src/main/java/com/humanbooster/services/UserService.java
package com.humanbooster.services;

import com.humanbooster.examthymeleaf.model.User;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private Map<Long, User> users = new HashMap<>();
    private Long nextId = 1L;
    
    public UserService() {
        // Données de test
        createUser("Alice");
        createUser("Bob");
        createUser("Charlie");
        createUser("Diana");
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public User getUserById(Long id) {
        return users.get(id);
    }
    
    public User createUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être vide");
        }
        
        User user = new User(nextId++, username.trim());
        users.put(user.getId(), user);
        return user;
    }
    
    public boolean existsById(Long id) {
        return users.containsKey(id);
    }
    
    public void deleteUser(Long id) {
        users.remove(id);
    }
    
    public User updateUser(Long id, String username) {
        User user = users.get(id);
        if (user != null) {
            user.setUsername(username);
        }
        return user;
    }
}