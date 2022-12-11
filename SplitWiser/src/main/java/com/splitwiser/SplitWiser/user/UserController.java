package com.splitwiser.SplitWiser.user;

import com.splitwiser.SplitWiser.group.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final GroupService groupService;

    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @PostMapping("/{firstName}/{lastName}/{groupId}")
    public void postUser(@PathVariable String firstName, @PathVariable  String lastName, @PathVariable Long groupId) {
        userService.postUser(firstName, lastName, groupId);
    }

}
