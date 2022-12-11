package com.splitwiser.SplitWiser.user;


import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
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

    public User getUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

    public void postUser(String firstName, String lastName, Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            User user = new User(firstName,lastName, group.get());
            userRepository.save(user);
        }
    }
}
