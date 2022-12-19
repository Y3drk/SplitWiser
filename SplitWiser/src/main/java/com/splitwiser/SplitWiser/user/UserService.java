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
        return userRepository.findById(id).orElse(null);
    }

    public List<Payment> getUserPayments(int userId) {
        User user = getUserById(userId);
        return userRepository.findAllUserPayments(user.getGroup().getId(), userId);
    }

    public int addUser(User user, int groupId) {
        return groupRepository.findById(groupId).map(foundGroup -> {
            user.setGroup(foundGroup);
            foundGroup.addMember(user);
            User addedUser = userRepository.save(user);
            groupRepository.save(foundGroup);
            return addedUser.getId();
        }).orElse(-1);
    }
}
