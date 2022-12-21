package com.splitwiser.SplitWiser.user;


import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
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
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/payments")
    public List<Payment> getUserPayments(@PathVariable int id) {
        return userService.getUserPayments(id);
    }

    @PostMapping("/groups/{groupId}")
    @ResponseBody
    public User addUser(@RequestBody User user, @PathVariable int groupId) {
        userService.addUser(user, groupId);
        return user;
    }

}
