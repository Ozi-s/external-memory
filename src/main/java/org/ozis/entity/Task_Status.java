package org.ozis.entity;

import jakarta.persistence.*;

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
    private int id;

    @Column(nullable = false)
    private String name;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Task_Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
