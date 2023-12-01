package com.example.PersonalAccounting.services.crud_seervice_impl;

import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.repositories.UserRepository;
import com.example.PersonalAccounting.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements CrudService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User create(User user) {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            return userRepository.save(user);
        }else {
            throw new IllegalArgumentException("User with this email is already exist.");
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getOne(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user with such id: " + id));
    }

    @Transactional(readOnly = true)
    public User getOne(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("No user with such email: " + email));
    }

    @Transactional
    public User update(int id, User user) {
        userRepository.findById(id).ifPresentOrElse((u) -> {
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            u.setFunds(user.getFunds());
        }, () -> {
            throw new NoSuchElementException("No user with id: " + id);
        });
        return getOne(id);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
