package info.diwe.todoapp.controller;

import info.diwe.todoapp.model.Category;
import info.diwe.todoapp.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;
    private List<Category> categories;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostConstruct
    public void init() {
        categories = new ArrayList<>();
    }

    @GetMapping("/list")
    public ModelAndView getCategoryList(ModelAndView modelAndView) {
        categories = getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("categories/list");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addCategoryGet(ModelAndView modelAndView) {
        Category category = new Category();
        modelAndView.addObject("category", category);
        modelAndView.setViewName("categories/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addCategoryPost(@ModelAttribute @Valid Category category, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView = showMsg("add", category, "Fehler beim Speichern der Kategorie ...", false, modelAndView);
            return modelAndView;
        }
        categoryService.createCategory(category);
        modelAndView = showMsg("add", category, "Kategorie erfolgreich hinzugefügt ...", true, modelAndView);
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateCategoryGet(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Category category = categoryService.readCategory(id);

        modelAndView.addObject("category", category);
        modelAndView.setViewName("categories/update");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateCategoryPost(@ModelAttribute @Valid Category category, BindingResult bindingResult,  ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView = showMsg("update", category, "Fehler beim Speichern der Kategorie ...", false, modelAndView);
            return modelAndView;
        }
        categoryService.updateCategory(category);

        modelAndView = showMsg("add", category, "Kategorie erfolgreich geändert ...", true, modelAndView);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Long id, ModelAndView modelAndView) {

        categoryService.deleteCategory(id);

        categories = getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("categories/list");
        return modelAndView;
    }

    private ModelAndView showMsg(String type, Category category, String msg, boolean is_success,
                                 ModelAndView modelAndView) {
        modelAndView.addObject("message", msg);

        if (is_success) {
            modelAndView.addObject("alertClass", "alert-success");
        } else {
            modelAndView.addObject("alertClass", "alert-danger");
        }
        modelAndView.addObject("category", category);
        modelAndView.setViewName("categories/" + type);
        return modelAndView;
    }

    private List<Category> getCategoryList() {
        return categoryService.readCategories();
    }

}
