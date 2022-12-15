package com.splitwiser.SplitWiser.util;


import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.group.GroupService;
import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.payment.PaymentRepository;
import com.splitwiser.SplitWiser.payment.PaymentService;
import com.splitwiser.SplitWiser.user.User;
import com.splitwiser.SplitWiser.user.UserRepository;
import com.splitwiser.SplitWiser.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
public class AppConfigurator {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        GroupRepository groupRepository,
                                        PaymentRepository paymentRepository,
                                        UserService userService,
                                        GroupService groupService,
                                        PaymentService paymentService) {
        return args -> {

            if (groupRepository.count() == 0) {
                groupService.addGroup(new Group("Rome trip"));
                groupService.addGroup(new Group("Mediolan trip"));
                groupService.addGroup(new Group("Beer trip"));
            }
            if (userRepository.count() == 0) {
                userService.addUser(new User("Mikołaj", "Wielgos"), 1);
                userService.addUser(new User("Santa", "Claus"), 1);
                userService.addUser(new User("Rudolph", "Reindeer"), 1);
                userService.addUser(new User("Lord", "Vader"), 3);
                userService.addUser(new User("Luke", "Skywalker"), 3);
                userService.addUser(new User("Han", "Solo"), 3);
            }

            if (paymentRepository.count() == 0) {
                paymentService.addPayment(new Payment(BigDecimal.valueOf(100), LocalDate.now(), "dinner bill"), 1, 1, List.of(2, 3));
                paymentService.addPayment(new Payment(BigDecimal.valueOf(300), LocalDate.now(), "bill"), 1, 2, List.of(3));
                paymentService.addPayment(new Payment(BigDecimal.valueOf(80), LocalDate.now(), "bill 2"), 3, 4, List.of(5, 6));
            }

            List<Group> allGroups = groupService.getGroups();
            List<User> allUsers = userService.getUsers();
            List<Payment> allPayments = paymentService.getPayments();

            System.out.println("___GROUPS___");
            for (Group group : allGroups) {
                System.out.println(group.getName() + " " +  group.getId());
            }
            System.out.println("___USERS___");
            for (User user : allUsers) {
                System.out.println(user.getFirstName() + " " + user.getLastName());
            }
            System.out.println("___PAYMENTS___");
            for (Payment payment : allPayments) {
                System.out.println(" group id: " + payment.getGroup().getId() + " payer name: " + payment.getPayer().getFirstName() + " description: " + payment.getDescription());

            }
        };
    };
};
