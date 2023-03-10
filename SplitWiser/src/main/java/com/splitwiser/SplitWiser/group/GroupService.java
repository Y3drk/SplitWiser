package com.splitwiser.SplitWiser.group;

import com.splitwiser.SplitWiser.category.Category;
import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(int id) {
        Optional<Group> result = this.groupRepository.findById(id);
        return result.orElse(null);
    }

    public List<User> getGroupMembers(int id) {
        Group group = getGroup(id);
        return group.getMembers();
    }

    public List<Payment> getGroupPayments(int id) {
        Group group = getGroup(id);
        return group.getPayments();
    }

    public List<Payment> getGroupCategoryPayments(int id, Category category) {
        return getGroupPayments(id).stream().filter(payment ->
            payment.getCategory() == category).toList();
    }

    public int addGroup(Group group) {
        return groupRepository.save(group).getId();
    }
}
