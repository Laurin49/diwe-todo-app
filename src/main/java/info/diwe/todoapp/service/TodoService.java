package info.diwe.todoapp.service;

import info.diwe.todoapp.model.Category;
import info.diwe.todoapp.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> readTodos();
    List<Todo> readTodosByCategory();
    Todo readTodo(Long id);
    Todo createTodo(Todo todo);
    Todo createTodoByCategory(Category category, Todo todo);
    Todo updateTodoByCategory(Category category, Todo todo);
    Todo updateTodo(Todo todo);
    void deleteTodo(Long id);
    List<Todo> findByCategoryName(String name);
    List<Todo> findFirstByCategory(Category category);
}
