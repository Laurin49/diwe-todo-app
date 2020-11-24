package info.diwe.todoapp.controller;

import info.diwe.todoapp.model.Category;
import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.model.filter.FilterCategory;
import info.diwe.todoapp.service.CategoryService;
import info.diwe.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private TodoService todoService;
    private CategoryService categoryService;

    private List<Todo> todos;
    private List<Category> categories;
    private List<String> selCategories;
    private FilterCategory filterCategory;

//    @Value("${todo.show.notErledigt}")
//    private String todo_show_notErledigt;
    // @Value("${todo.show.category}")
//    private String todo_show_category;

    public TodoController(TodoService todoService, CategoryService categoryService) {
        this.todoService = todoService;
        this.categoryService = categoryService;
    }

    @PostConstruct
    public void init() {
        todos = new ArrayList<>();
        categories = new ArrayList<>();
        selCategories = new ArrayList<>();

        filterCategory = new FilterCategory();
        Category category = categoryService.findFirstBy();
        filterCategory.setFilterCategory(category.getName());

    }

    @GetMapping("/list")
    public ModelAndView getTodoList(ModelAndView modelAndView) {

        categories.clear();
        selCategories.clear();
        categoryService.readCategories().iterator().forEachRemaining(categories::add);
        for (Category category: categories) {
            selCategories.add(category.getName());
        }

        todos = holeTodos(filterCategory.getFilterCategory());

        selCategoriesContainsAlle();

        modelAndView.addObject("filterCategory", filterCategory);
        modelAndView.addObject("selCategories", selCategories);
        modelAndView.addObject("todos", todos);
        modelAndView.setViewName("todos/list");
        return modelAndView;
    }

    @PostMapping("/filter")
    public ModelAndView getTodosFiltered(@ModelAttribute FilterCategory m_filterCategory, ModelAndView modelAndView) {
        filterCategory.setFilterCategory(m_filterCategory.getFilterCategory());

        todos = holeTodos(filterCategory.getFilterCategory());

        selCategoriesContainsAlle();



        modelAndView.addObject("filterCategory", filterCategory);
        modelAndView.addObject("selCategories", selCategories);
        modelAndView.addObject("todos", todos);
        modelAndView.setViewName("todos/list");
        return modelAndView;
    }

    private void selCategoriesContainsAlle() {
        if (!selCategories.contains("Alle")) {
            selCategories.add(0, "Alle");
        }
    }

    private List<Todo> holeTodos(String filter) {
        if (filter.equals("Alle")) {
           return todoService.readTodos();
        } else {
            return todoService.findByCategoryName(filter);
        }
    }


    @GetMapping("/add")
    public ModelAndView addTodoGet(ModelAndView modelAndView) {
        Todo todo = new Todo();
        modelAndView.addObject("todo", todo);
        modelAndView.setViewName("todos/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addTodoPost(@ModelAttribute @Valid Todo todo, BindingResult bindingResult,  ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView = showMsg("add", todo, "Fehler beim Speichern des Todo ...", false, modelAndView);
            return modelAndView;
        }
        System.out.println("Erledigt: " + todo.getErledigt());
        if (todo.getErledigt()) {
            todo.setPrioritaet((byte) 9);
        }
        todoService.createTodoByCategory(categoryService.findByName(filterCategory.getFilterCategory()), todo);

        modelAndView = showMsg("add", todo, "Todo erfolgreich hinzugefügt ...", true, modelAndView);
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateTodoGet(@PathVariable("id") Long id,  ModelAndView modelAndView) {
        Todo todo = todoService.readTodo(id);

        modelAndView.addObject("todo", todo);
        modelAndView.setViewName("todos/update");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateTodoPost(@ModelAttribute @Valid Todo todo, BindingResult bindingResult,  ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView = showMsg("update", todo, "Fehler beim Speichern des Todo ...", false, modelAndView);
            return modelAndView;
        }
        if (filterCategory.getFilterCategory().equals("Alle")) {
            modelAndView = showMsg("update", todo, "Kategorie nicht selektiert - Updaten nicht möglich ...", false, modelAndView);
            return modelAndView;
        }
        System.out.println("Update - Erledigt: " + todo.getErledigt());
        if (todo.getErledigt()) {
            todo.setPrioritaet((byte) 9);
        }
        todoService.updateTodoByCategory(categoryService.findByName(filterCategory.getFilterCategory()), todo);

        modelAndView = showMsg("add", todo, "Todo erfolgreich geändert ...", true, modelAndView);
        return modelAndView;
    }

    private ModelAndView showMsg(String type, Todo todo, String msg, boolean is_success,
                                 ModelAndView modelAndView) {
        modelAndView.addObject("message", msg);

        if (is_success) {
            modelAndView.addObject("alertClass", "alert-success");
        } else {
            modelAndView.addObject("alertClass", "alert-danger");
        }
        modelAndView.addObject("filterCategory", filterCategory);
        modelAndView.addObject("selCategories", selCategories);
        modelAndView.addObject("todo", todo);
        modelAndView.setViewName("todos/" + type);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteTodo(@PathVariable("id") Long id, ModelAndView modelAndView) {

        todoService.deleteTodo(id);

        todos = holeTodos(filterCategory.getFilterCategory());
        modelAndView.addObject("filterCategory", filterCategory);
        modelAndView.addObject("selCategories", selCategories);
        modelAndView.addObject("todos", todos);
        modelAndView.setViewName("todos/list");
        return modelAndView;
    }

    private List<Todo> getTodoList() {
        return todoService.readTodos();
    }
}
