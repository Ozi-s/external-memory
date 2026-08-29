package org.ozis.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_status")
public class Task_Status {

    public Task_Status() {
    }

    public Task_Status(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "task_status")   // ← исправлено: поле в Task называется taskStatus
    private List<Task> tasks;

    public Integer getId() {               // лучше Integer, чтобы избежать NPE
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Task_Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}