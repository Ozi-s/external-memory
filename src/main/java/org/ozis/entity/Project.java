package org.ozis.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    public Project() {
    }

    public Project(String place, String name) {
        this.place = place;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                   // лучше Integer

    @Column(nullable = false)
    private String place;

    private String name;

    @OneToMany(mappedBy = "project_id")      // ← исправлено
    private List<Desigion> desigions;

    @OneToMany(mappedBy = "project_id")      // ← исправлено
    private List<Task> tasks;

    @OneToMany(mappedBy = "project_id")      // ← исправлено
    private List<Context> contexts;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Desigion> getDesigions() {
        return desigions;
    }
    public void setDesigions(List<Desigion> desigions) {
        this.desigions = desigions;
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Context> getContexts() {
        return contexts;
    }
    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    @Override
    public String toString() {
        return "Project{" +
                "place='" + place + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}