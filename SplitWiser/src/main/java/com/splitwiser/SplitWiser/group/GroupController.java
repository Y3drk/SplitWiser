package com.splitwiser.SplitWiser.group;

import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/groups")
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
    public Group getGroup(@PathVariable int id) {
        return groupService.getGroup(id);
    }

    @GetMapping("/{id}/members")
    public List<User> getGroupMembers(@PathVariable int id) {
        return groupService.getGroupMembers(id);
    }

    @GetMapping("/{id}/payments")
    public List<Payment> getGroupPayments(@PathVariable int id) {
        return groupService.getGroupPayments(id);
    }

    @PostMapping("")
    @ResponseBody
    public Group addGroup(@RequestBody Group group) {
        groupService.addGroup(group);
        return group;
    }

}
