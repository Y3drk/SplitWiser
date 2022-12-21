package com.splitwiser.SplitWiser.user;

import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.payment.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;;
import org.springframework.test.context.TestPropertySource;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.profiles.include=test"})
@TestPropertySource(
        locations = "classpath:applicationTest.properties")
public class UserServiceIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser() {
//        GIVEN
        Group group = new Group("animal group");
        int groupId = groupRepository.save(group).getId();

        User user = new User("Garfield", "Cat", group);
        int userId = userService.addUser(user, groupId);

//        WHEN
        User resultUser = userService.getUserById(userId);

//        THEN
        assertThat(resultUser.getGroup().getId()).isEqualTo(groupId);
        assertThat(resultUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(resultUser.getLastName()).isEqualTo(user.getLastName());
    }

    @Test
    public void testGetUserPayments() {
//        GIVEN
        List<Integer> correctPaymentsIds = new ArrayList<>();
        Group group = new Group("animal group");
        Group savedGroup = groupRepository.save(group);

        User user1 = new User("Garfield", "Cat", savedGroup);
        User savedUser1 = userRepository.save(user1);

        User user2 = new User("Scooby", "Doo", savedGroup);
        User savedUser2 = userRepository.save(user2);

        User user3 = new User("Shaun", "The Sheep", savedGroup);
        User savedUser3 = userRepository.save(user3);

        Payment payment1 = new Payment(savedUser1, List.of(savedUser2, savedUser3), savedGroup, BigDecimal.valueOf(100), LocalDate.now(), "I love pizza");
        correctPaymentsIds.add(paymentRepository.save(payment1).getId());

        Payment payment2 = new Payment(savedUser2, List.of(savedUser3), savedGroup, BigDecimal.valueOf(70), LocalDate.now(), "Scooby is cool");
        paymentRepository.save(payment2);

        Payment payment3 = new Payment(savedUser3, List.of(savedUser1), savedGroup, BigDecimal.valueOf(10), LocalDate.now(), "Garfield is lazy");
        correctPaymentsIds.add(paymentRepository.save(payment3).getId());

//        WHEN
        List<Payment> userPayments = userService.getUserPayments(savedUser1.getId());
        List<Integer> userPaymentsIds = userPayments.stream().map(Payment::getId).toList();


//        THEN
        assertThat(userPaymentsIds)
                .containsExactlyInAnyOrderElementsOf(correctPaymentsIds)
                .usingRecursiveComparison();
    }
}
