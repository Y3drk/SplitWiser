package com.splitwiser.SplitWiser.user;


import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(int id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

//   take all user group payments without receiver && single payments if user is payer or receiver
    public List<Payment> getUserPayments(int id) {
        User user = getUserById(id);
        return userRepository.findAllUserPayments(user.getGroup().getId(), id);
    }

    public void addUser(String firstName, String lastName, int groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            User user = new User(firstName,lastName, group.get());
            userRepository.save(user);
        }
    }
}
