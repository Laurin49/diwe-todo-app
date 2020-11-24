package info.diwe.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Der Name des Todos darf nicht leer bleiben ...")

    @Size(min = 3, max = 50, message = "Der Name des Todos muss zwischen 3 und 50 Zeichen enthalten ...")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String beschreibung;

    @Min(value = 1, message = "Der Wert von Priorität sollte mindestens 1 haben ...")
    @Max(value = 9, message = "Der Wert von Priorität sollte maximal bei 9 liegen ...")
    @Column(columnDefinition = "integer default 9")
    private Byte prioritaet;
    private Boolean erledigt = null;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    public Todo() {
    }

    public Todo(String name, String beschreibung) {
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Byte getPrioritaet() {
        return prioritaet;
    }

    public void setPrioritaet(Byte prioritaet) {
        this.prioritaet = prioritaet;
    }

    public Boolean getErledigt() {
        return erledigt;
    }

    public void setErledigt(Boolean erledigt) {
        this.erledigt = erledigt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
