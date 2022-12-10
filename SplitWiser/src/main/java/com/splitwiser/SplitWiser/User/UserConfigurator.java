package com.splitwiser.SplitWiser.User;

import com.splitwiser.SplitWiser.Group.Group;
import com.splitwiser.SplitWiser.Group.GroupService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfigurator {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, GroupService groupService) {
        return args -> {
            if (userRepository.count() == 0) {
                Group group = groupService.getGroupById(1L);
                User user = new User("Mikołaj", "Wielgos", group);
                userRepository.save(user);
            }
        };
    }
}
