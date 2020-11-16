package info.diwe.todoapp.service.impl;

import info.diwe.todoapp.exception.ResourceNotFoundException;
import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.repository.TodoRepository;
import info.diwe.todoapp.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> readTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos;
    }

    @Override
    public Todo readTodo(Long id) {
        Optional<Todo> optTodo = todoRepository.findById(id);
        if (!optTodo.isPresent()) {
            throw new ResourceNotFoundException("Todo mit id: " + id + " not found ...");
        }
        return optTodo.get();
    }

    @Override
    public Todo createTodo(Todo todo) {
        Todo result = todoRepository.save(todo);
        return result;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        Optional<Todo> optTodo = todoRepository.findById(todo.getId());
        if (!optTodo.isPresent()) {
            throw new ResourceNotFoundException("Todo mit id: " + todo.getId() + " not found ...");
        }
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        Optional<Todo> optTodo = todoRepository.findById(id);
        if (!optTodo.isPresent()) {
            throw new ResourceNotFoundException("Todo mit id: " + id + " not found ...");
        }
        todoRepository.delete(optTodo.get());
    }
}
