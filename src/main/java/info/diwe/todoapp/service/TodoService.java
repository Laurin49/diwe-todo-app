package info.diwe.todoapp.service;

import info.diwe.todoapp.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> readTodos();
    Todo readTodo(Long id);
    Todo createTodo(Todo todo);
    Todo updateTodo(Todo todo);
    void deleteTodo(Long id);
}
