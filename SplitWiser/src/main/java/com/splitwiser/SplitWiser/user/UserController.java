package com.splitwiser.SplitWiser.user;


import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/{id}/payments")
    public List<Payment> getUserPayments(@PathVariable int id) {
        return userService.getUserPayments(id);
    }

    @PostMapping("/user/{firstName}/{lastName}/{groupId}")
    public void addUser(@PathVariable String firstName, @PathVariable  String lastName, @PathVariable int groupId) {
        userService.addUser(firstName, lastName, groupId);
    }

}
