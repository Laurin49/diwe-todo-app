package info.diwe.todoapp.bootstrap;

import info.diwe.todoapp.model.Todo;
import info.diwe.todoapp.service.TodoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    
    private TodoService todoService;

    public DataLoader(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Todo> todos = new ArrayList<>();
        todoService.readTodos().iterator().forEachRemaining(todos::add);
        if (todos.size() == 0) {
            initTodoData();
        }
    }

    private void initTodoData() {
        Todo todo1 = new Todo("Termin Steuerberater", "Termin beim Steuerberater Prohl für Dezember machen ...");
        todoService.createTodo(todo1);
        Todo todo2 = new Todo("Termin Zahnarzt ", "Zahnarzttermin Dr. Strickling - Vollständige Zahnreinigung ...");
        todoService.createTodo(todo2);
        Todo todo3 = new Todo("Termin EFL", "Termin bei Frau Kieslich EFL Recklinghausen - Anfang Dezember ...");
        todoService.createTodo(todo3);
        Todo todo4 = new Todo("Rechnung Zahnarzt", "Rechnung für Vollständige Zahnreinigung bei der DAK einreichen ...");
        todoService.createTodo(todo4);
        Todo todo5 = new Todo("Personalausweis beantragen", "Personalausweis bei der Stadt beantragen und Fotos machen ...");
        todoService.createTodo(todo5);
        Todo todo6 = new Todo("Führerschein beantragen", "Führerschein bei der Stadt beantragen, und Fotos machen ...");
        todoService.createTodo(todo6);
    }
}
