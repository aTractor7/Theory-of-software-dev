package com.example.PersonalAccounting.services;

import com.example.PersonalAccounting.model.User;
import com.example.PersonalAccounting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }else {
            throw new IllegalArgumentException("User with this email is already exist.");
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user with such id: " + id));
    }

    public User getOne(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user with such email: " + email));
    }

    @Transactional
    public void update(int id, User user) {
        userRepository.findById(id).ifPresentOrElse((u) -> {
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            u.setFunds(user.getFunds());
        }, () -> {
            throw new IllegalArgumentException("No user with id: " + id);
        });
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
