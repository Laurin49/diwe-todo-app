package info.diwe.todoapp.repository;

import info.diwe.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCategory_Name(String name);
    List<Todo> findByCategory_NameOrderByPrioritaetAsc(String name);

    List<Todo> findAllByOrderByCategoryNameAsc();

    @Query("SELECT t from Todo t ORDER BY t.category.name")
    List<Todo> readTodosByCategory();
}
