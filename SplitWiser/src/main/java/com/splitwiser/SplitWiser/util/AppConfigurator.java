package com.splitwiser.SplitWiser.util;


import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.group.GroupService;
import com.splitwiser.SplitWiser.user.User;
import com.splitwiser.SplitWiser.user.UserRepository;
import com.splitwiser.SplitWiser.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfigurator {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        GroupRepository groupRepository,
                                        UserService userService,
                                        GroupService groupService) {
        return args -> {
            if (userRepository.count() == 0) {
                Group group = groupService.getGroupById(1L);
                User user = new User("Mikołaj", "Wielgos", group);
                userRepository.save(user);
            }
            if (groupRepository.count() == 0) {
                Group firstGroup = new Group("Rome Trip");
                groupRepository.save(firstGroup);
            }

            List<Group> allGroups = groupService.getGroups();
            List<User> allUsers = userService.getUsers();

            System.out.println("___GROUPS___");
            for (Group group : allGroups) {
                System.out.println(group.getName());
            }
            System.out.println("___USERS___");
            for (User user : allUsers) {
                System.out.println(user.getFirstName() + " " + user.getLastName());
            }
        };
    }
}