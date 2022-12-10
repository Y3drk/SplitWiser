package com.splitwiser.SplitWiser.Group;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupConfigurator {
    @Bean
    CommandLineRunner commandLineRunner(GroupRepository groupRepository) {
        return args -> {
            if (groupRepository.count() == 0) {
                Group firstGroup = new Group("Rome Trip");
                groupRepository.save(firstGroup);
            }
        };
    }
}
