package com.splitwiser.SplitWiser.category;

import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    @GetMapping("")
    public List<Category> getCategories() {
        return List.of(Category.values());
    }
}
