package com.splitwiser.SplitWiser.User;


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

    @PostMapping("/user/group/{groupId}")
    public void addUser(@RequestBody User user, @PathVariable int groupId) {
        userService.addUser(user, groupId);
    }

}
