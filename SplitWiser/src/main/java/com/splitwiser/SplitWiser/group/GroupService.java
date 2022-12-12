package com.splitwiser.SplitWiser.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public GroupService(){
    }

    public List<Group> getGroups() {
        return this.groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        Optional<Group> result =  this.groupRepository.findById(id);
        return result.orElse(null);
    }



}
