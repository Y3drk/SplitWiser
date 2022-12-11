package com.splitwiser.SplitWiser.user;

import com.splitwiser.SplitWiser.group.GroupService;
import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @GetMapping("/{id}/payments")
    public List<Payment> getUserPayments(@PathVariable String id) {
        return userService.getUserPayments(Long.parseLong(id));
    }

    @PostMapping("/{firstName}/{lastName}/{groupId}")
    public void postUser(@PathVariable String firstName, @PathVariable  String lastName, @PathVariable Long groupId) {
        userService.postUser(firstName, lastName, groupId);
    }

}
