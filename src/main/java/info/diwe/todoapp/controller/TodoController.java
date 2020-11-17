package info.diwe.todoapp.controller;

import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.service.TodoService;
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
    private List<Todo> todos;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostConstruct
    public void init() {
        todos = new ArrayList<>();
    }

    @GetMapping("/list")
    public ModelAndView getTodoList(ModelAndView modelAndView) {
        todos = getTodoList();
        modelAndView.addObject("todos", todos);
        modelAndView.setViewName("todos/list");
        return modelAndView;
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
        todoService.createTodo(todo);
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
        todoService.updateTodo(todo);
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
        modelAndView.addObject("todo", todo);
        modelAndView.setViewName("todos/" + type);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteTodo(@PathVariable("id") Long id, ModelAndView modelAndView) {

        todoService.deleteTodo(id);

        todos = getTodoList();
        modelAndView.addObject("todos", todos);
        modelAndView.setViewName("todos/list");
        return modelAndView;
    }

    private List<Todo> getTodoList() {
        return todoService.readTodos();
    }
}
