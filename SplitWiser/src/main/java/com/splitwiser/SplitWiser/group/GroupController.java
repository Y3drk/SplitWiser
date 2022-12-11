package com.splitwiser.SplitWiser.group;

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

    @PostMapping("/{name}")
    public void postGroup(@PathVariable String name) {
        groupService.postGroup(name);
    }
}
