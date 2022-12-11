package com.splitwiser.SplitWiser.group;

import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("")
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable String id) {
        return groupService.getGroup(Long.parseLong(id));
    }

    @GetMapping("/{id}/members")
    public List<User> getGroupMembers(@PathVariable String id) {
        return groupService.getGroupMembers(Long.parseLong(id));
    }

    @GetMapping("/{id}/payments")
    public List<Payment> getGroupPayments(@PathVariable String id) {
        return groupService.getGroupPayments(Long.parseLong(id));
    }

    @PostMapping("/{name}")
    public void postGroup(@PathVariable String name) {
        groupService.postGroup(name);
    }

}
