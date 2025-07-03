package com.humanbooster.services;

import com.humanbooster.examthymeleaf.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private Long nextId = 1L;

    public UserService() {
        // Ajout d'utilisateurs de test
        createUser("admin");
        createUser("john_doe");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public User createUser(String username) {
        User user = new User(nextId, username);
        users.put(nextId, user);
        nextId++;
        return user;
    }

    public boolean existsById(Long id) {
        return users.containsKey(id);
    }
}
