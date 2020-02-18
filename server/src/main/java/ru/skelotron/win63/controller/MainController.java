package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skelotron.win63.controller.model.CategoryModel;
import ru.skelotron.win63.entity.entity.CategoryEntity;
import ru.skelotron.win63.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final CategoryRepository categoryRepository;

    @Autowired
    public MainController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryRepository.findAll()) {
            categoryModelList.add(new CategoryModel(categoryEntity.getName(), categoryEntity.getUrl()));
        }
        model.addAttribute("categories", categoryModelList);
        return "index";
    }
}
