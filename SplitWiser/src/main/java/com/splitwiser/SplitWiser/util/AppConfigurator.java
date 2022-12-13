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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
                groupService.addGroup("Rome trip");
                groupService.addGroup("Mediolan trip");
                groupService.addGroup("Beer trip");
            }
            if (userRepository.count() == 0) {
                userService.addUser("Mikołaj", "Wielgos", 1);
                userService.addUser("Santa", "Claus", 1);
                userService.addUser("Rudolph", "Reindeer", 1);
                userService.addUser("Lord", "Vader", 3);
                userService.addUser("Luke", "Skywalker", 3);
                userService.addUser("Han", "Solo", 3);
            }

            if (paymentRepository.count() == 0) {
                paymentService.addPayment(1, BigDecimal.valueOf(100), new Date(), "dinner bill", 1, java.util.Optional.empty());
                paymentService.addPayment(1, BigDecimal.valueOf(200), new Date(), "Coliseum tickets", 1, java.util.Optional.empty());
                paymentService.addPayment(1, BigDecimal.valueOf(50), new Date(), "souvenirs", 2, Optional.of(3));

                paymentService.addPayment(3, BigDecimal.valueOf(30), new Date(), "perełka", 4, Optional.empty());
                paymentService.addPayment(3, BigDecimal.valueOf(50), new Date(), "litovel", 5, Optional.empty());
                paymentService.addPayment(3, BigDecimal.valueOf(60), new Date(), "litovel v2", 6, Optional.of(5));
            }


            List<Group> allGroups = groupService.getGroups();
            List<User> allUsers = userService.getUsers();
            List<Payment> allPayments = paymentService.getPayments();

            System.out.println("___GROUPS___");
            for (Group group : allGroups) {
                System.out.println(group.getName() + group.getId());
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
    }
}