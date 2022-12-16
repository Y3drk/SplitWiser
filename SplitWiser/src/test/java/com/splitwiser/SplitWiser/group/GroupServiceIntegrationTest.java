package com.splitwiser.SplitWiser.group;


import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.payment.PaymentRepository;
import com.splitwiser.SplitWiser.user.User;
import com.splitwiser.SplitWiser.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.profiles.include=test"})
@TestPropertySource(
        locations = "classpath:applicationTest.properties")
class GroupServiceIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private GroupService groupService;

    @Test
    public void shouldSaveGroup() {
        Group group = new Group("animal group");
        int groupId = groupService.addGroup(group);

        Group resultGroup = groupService.getGroup(groupId);
        assertThat(resultGroup.getId()).isEqualTo(groupId);
        assertThat(resultGroup.getName()).isEqualTo(group.getName());
    }

    @Test
    public void shouldGetGroupMembers() {
        List<Integer> correctMembersIds = new ArrayList<>();
        Group group1 = new Group("animal group");
        Group savedGroup1 = groupRepository.save(group1);

        Group group2 = new Group("animal group");
        Group savedGroup2 = groupRepository.save(group2);

        User user1 = new User("Garfield", "Cat", savedGroup1);
        correctMembersIds.add(userRepository.save(user1).getId());

        User user2 = new User("Scooby", "Doo", savedGroup1);
        correctMembersIds.add(userRepository.save(user2).getId());

        User user3 = new User("Shaun", "The Sheep", savedGroup2);
        userRepository.save(user3);

        User user4 = new User("Odie", "Dog", savedGroup1);
        correctMembersIds.add(userRepository.save(user4).getId());

        List<User> groupMembers = groupService.getGroupMembers(savedGroup1.getId());
        List<Integer> groupMembersIds = groupMembers.stream().map(User::getId).toList();

        assertThat(groupMembersIds)
                .containsExactlyInAnyOrderElementsOf(correctMembersIds)
                .usingRecursiveComparison();
    }

    @Test
    public void shouldGetGroupPayments() {
        List<Integer> correctPaymentsIds = new ArrayList<>();
        Group group1 = new Group("animal group");
        Group savedGroup1 = groupRepository.save(group1);

        Group group2 = new Group("animal group");
        Group savedGroup2 = groupRepository.save(group2);

        User user1 = new User("Garfield", "Cat", savedGroup1);
        User savedUser1 = userRepository.save(user1);

        User user2 = new User("Scooby", "Doo", savedGroup1);
        User savedUser2 = userRepository.save(user2);

        User user3 = new User("Shaun", "The Sheep", savedGroup2);
        User savedUser3 = userRepository.save(user3);

        Payment payment1 = new Payment(savedUser1, List.of(savedUser2), savedGroup1, BigDecimal.valueOf(100), LocalDate.now(), "I love pizza");
        correctPaymentsIds.add(paymentRepository.save(payment1).getId());

        Payment payment2 = new Payment(savedUser3, List.of(savedUser3), savedGroup2, BigDecimal.valueOf(70), LocalDate.now(), "Scooby is cool");
        paymentRepository.save(payment2);

        Payment payment3 = new Payment(savedUser2, List.of(savedUser1, savedUser2), savedGroup1, BigDecimal.valueOf(10), LocalDate.now(), "Garfield is lazy");
        correctPaymentsIds.add(paymentRepository.save(payment3).getId());

        List<Payment> groupPayments = groupService.getGroupPayments(savedGroup1.getId());
        List<Integer> groupPaymentsIds = groupPayments.stream().map(Payment::getId).toList();

        assertThat(groupPaymentsIds)
                .containsExactlyInAnyOrderElementsOf(correctPaymentsIds)
                .usingRecursiveComparison();
    }
}