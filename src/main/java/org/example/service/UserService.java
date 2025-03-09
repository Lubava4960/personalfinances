package org.example.service;

import org.example.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public boolean register(String email, String password, String name) {
        if (users.containsKey(email)) {
            return false; // email уже занят
        }
        users.put(email, new User(name, email, password));
        return true;
    }

    public User authenticate(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public boolean updateUser(String email, String newName, String newPassword) {
        User user = users.get(email);
        if (user != null) {
            user.setName(newName);
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }


    public boolean deleteUser(String email) {
        if (users.containsKey(email)) {
            users.remove(email);
            return true;
        }
        return false;
    }
}




