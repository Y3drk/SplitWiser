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
import java.util.Optional;

@Configuration
public class AppConfigurator {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        GroupRepository groupRepository,
                                        UserService userService,
                                        GroupService groupService) {
        return args -> {

            if (groupRepository.count() == 0) {
                groupService.postGroup("Rome trip");
                groupService.postGroup("Mediolan trip");
                groupService.postGroup("Beer trip");
            }
            if (userRepository.count() == 0) {
//                Group romeGroup = groupService.getGroup(1L);
//                Group beerGroup = groupService.getGroup(3L);
                userService.postUser("Mikołaj", "Wielgos", 1L);
                userService.postUser("Lord", "Vader", 3L);
                userService.postUser("Luke", "Skywalker", 3L);


////
//                User mikolajUser = new User("Mikołaj", "Wielgos", romeGroup);
//                User lordUser = new User("Lord", "Vader", beerGroup);
//                User lukeUser = new User("Luke", "Skywalker", beerGroup);
//                userRepository.saveAll(List.of(mikolajUser, lordUser, lukeUser));

//                Group beer2Group = groupService.getGroupById(4L);
//                System.out.println(beer2Group);

//                romeGroup.addMember(mikolajUser);
//                romeGroup.addMember();
            }


            List<Group> allGroups = groupService.getGroups();
            List<User> allUsers = userService.getUsers();

            System.out.println("___GROUPS___");
            for (Group group : allGroups) {
                System.out.println(group.getName() + group.getId());
            }
            System.out.println("___USERS___");
            for (User user : allUsers) {
                System.out.println(user.getFirstName() + " " + user.getLastName());
            }
        };
    }
}