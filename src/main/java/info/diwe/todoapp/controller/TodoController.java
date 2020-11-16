package info.diwe.todoapp.controller;

import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
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
