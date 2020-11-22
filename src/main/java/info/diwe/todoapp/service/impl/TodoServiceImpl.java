package info.diwe.todoapp.service.impl;

import info.diwe.todoapp.exception.ResourceNotFoundException;
import info.diwe.todoapp.model.Category;
import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.repository.CategoryRepository;
import info.diwe.todoapp.repository.TodoRepository;
import info.diwe.todoapp.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private CategoryRepository categoryRepository;

    public TodoServiceImpl(TodoRepository todoRepository, CategoryRepository categoryRepository) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Todo> readTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos;
    }

    @Override
    public List<Todo> readTodosByCategory() {
        List<Todo> todos = todoRepository.findAllByOrderByCategoryNameAsc();
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
    public Todo createTodoByCategory(Category category, Todo todo) {
        todo.setCategory(category);
        Todo result = todoRepository.save(todo);
        category.addTodo(result);
        categoryRepository.save(category);
        return result;
    }

    @Override
    public Todo updateTodoByCategory(Category category, Todo todo) {
        Optional<Todo> optTodo = todoRepository.findById(todo.getId());
        if (!optTodo.isPresent()) {
            throw new ResourceNotFoundException("Todo mit id: " + todo.getId() + " not found ...");
        }
        todo.setCategory(category);
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
        Category category = optTodo.get().getCategory();
        category.removeTodo(optTodo.get());
        categoryRepository.save(category);
        todoRepository.delete(optTodo.get());
    }

    @Override
    public List<Todo> findByCategoryName(String name) {
        return todoRepository.findByCategory_NameOrderByPrioritaetAsc(name);
    }

    @Override
    public List<Todo> findFirstByCategory(Category category) {
        return todoRepository.findFirstByCategory(category);
    }

}
