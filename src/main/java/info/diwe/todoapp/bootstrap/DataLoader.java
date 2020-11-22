package info.diwe.todoapp.bootstrap;

import info.diwe.todoapp.model.Category;
import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.service.CategoryService;
import info.diwe.todoapp.service.TodoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    
    private TodoService todoService;
    private CategoryService categoryService;

    public DataLoader(TodoService todoService, CategoryService categoryService) {
        this.todoService = todoService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Category> categories = new ArrayList<>();
        categoryService.readCategories().iterator().forEachRemaining(categories::add);
        if (categories.size() == 0) {
            initCategoryData();
        }
        List<Todo> todos = new ArrayList<>();
        todoService.readTodos().iterator().forEachRemaining(todos::add);
        if (todos.size() == 0) {
            initTodoData();
        }
    }

    private void initCategoryData() {
        Category category1 = new Category("App Development");
        categoryService.createCategory(category1);
        Category category2 = new Category("Bücher lesen");
        categoryService.createCategory(category2);
        Category category3 = new Category("Aufgaben privat");
        categoryService.createCategory(category3);
        Category category4 = new Category("Spring Boot");
        categoryService.createCategory(category4);
        Category category5 = new Category("Java Path");
        categoryService.createCategory(category5);
        Category category6 = new Category("OOP and Design Pattern");
        categoryService.createCategory(category6);
    }

    private void initTodoData() {
        Category category_privat = categoryService.findByName("Aufgaben privat");
        Todo todo1 = new Todo("Termin Steuerberater", "Termin beim Steuerberater Prohl für Dezember machen ...");
        todoService.createTodo(todo1);
        category_privat.addTodo(todo1);
        categoryService.updateCategory(category_privat);

        Todo todo2 = new Todo("Termin Zahnarzt ", "Zahnarzttermin Dr. Strickling - Vollständige Zahnreinigung ...");
        todoService.createTodo(todo2);
        category_privat.addTodo(todo2);
        categoryService.updateCategory(category_privat);

        Todo todo3 = new Todo("Termin EFL", "Termin bei Frau Kieslich EFL Recklinghausen - Anfang Dezember ...");
        todoService.createTodo(todo3);
        category_privat.addTodo(todo3);
        categoryService.updateCategory(category_privat);

        Todo todo4 = new Todo("Rechnung Zahnarzt", "Rechnung für Vollständige Zahnreinigung bei der DAK einreichen ...");
        todoService.createTodo(todo4);
        category_privat.addTodo(todo4);
        categoryService.updateCategory(category_privat);

        Todo todo5 = new Todo("Personalausweis beantragen", "Personalausweis bei der Stadt beantragen und Fotos machen ...");
        todoService.createTodo(todo5);
        category_privat.addTodo(todo5);
        categoryService.updateCategory(category_privat);

        Todo todo6 = new Todo("Führerschein beantragen", "Führerschein bei der Stadt beantragen, und Fotos machen ...");
        todoService.createTodo(todo6);
        category_privat.addTodo(todo6);
        categoryService.updateCategory(category_privat);

    }
}
