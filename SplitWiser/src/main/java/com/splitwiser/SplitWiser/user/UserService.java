package com.splitwiser.SplitWiser.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {

    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
}
