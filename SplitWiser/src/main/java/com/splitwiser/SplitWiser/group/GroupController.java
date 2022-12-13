package com.splitwiser.SplitWiser.group;

import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping("/group/{id}")
    public Group getGroup(@PathVariable int id) {
        return groupService.getGroup(id);
    }

    @GetMapping("group/{id}/members")
    public List<User> getGroupMembers(@PathVariable int id) {
        return groupService.getGroupMembers(id);
    }

    @GetMapping("/{id}/payments")
    public List<Payment> getGroupPayments(@PathVariable int id) {
        return groupService.getGroupPayments(id);
    }

    @PostMapping("/{name}")
    public void postGroup(@PathVariable String name) {
        groupService.postGroup(name);
    }

}
