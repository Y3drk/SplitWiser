package com.splitwiser.SplitWiser.payment;

import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.user.User;
import com.splitwiser.SplitWiser.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.profiles.include=test"})
@TestPropertySource(
        locations = "classpath:applicationTest.properties")
public class PaymentServiceIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PaymentService paymentService;

    @Test
    public void shouldSavePayment() {
        Group group1 = new Group("animal group");
        Group savedGroup1 = groupRepository.save(group1);

        User user1 = new User("Garfield", "Cat", savedGroup1);
        User savedUser1 = userRepository.save(user1);

        User user2 = new User("Scooby", "Doo", savedGroup1);
        User savedUser2 = userRepository.save(user2);

        Payment payment = new Payment(savedUser1, List.of(savedUser2), savedGroup1, BigDecimal.valueOf(70), LocalDate.now(), "Scooby is cool");
        int paymentId = paymentService.addPayment(payment,savedGroup1.getId(), savedUser1.getId(), List.of(savedUser2.getId()));

        Payment resultPayment = paymentService.getPayment(paymentId);
        assertThat(resultPayment.getId()).isEqualTo(paymentId);
    }
}
