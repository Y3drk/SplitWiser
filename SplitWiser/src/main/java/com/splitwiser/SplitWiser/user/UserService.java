package com.splitwiser.SplitWiser.user;


import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Payment> getUserPayments(int userId) {
        User user = getUserById(userId);
        return userRepository.findAllUserPayments(user.getGroup().getId(), userId);
    }

    public void addUser(User user, int groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            user.setGroup(group.get());
            group.get().addMember(user);
            userRepository.save(user);
            groupRepository.save(group.get());
        }
    }
}
