package org.ozis.entity;

import jakarta.persistence.*;

@Entity
@Table(name="project")
public class Project {

    public Project(String place, String name, int task_id, int desigion_id, int context_id) {
        this.place = place;
        this.name = name;
        this.task_id = task_id;
        this.desigion_id = desigion_id;
        this.context_id = context_id;
    }

    public Project() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String place;

    private String name;

    private int task_id;

    private int desigion_id;

    private int context_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getDesigion_id() {
        return desigion_id;
    }

    public void setDesigion_id(int desigion_id) {
        this.desigion_id = desigion_id;
    }

    public int getContext_id() {
        return context_id;
    }

    public void setContext_id(int context_id) {
        this.context_id = context_id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", name='" + name + '\'' +
                ", task_id=" + task_id +
                ", desigion_id=" + desigion_id +
                ", context_id=" + context_id +
                '}';
    }
}
