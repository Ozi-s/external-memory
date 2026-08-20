package org.ozis.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="table")
public class Task {

    public Task() {
    }

    public Task(String name, int execution_period, int task_status, String files, String description, LocalDate deadline) {
        this.name = name;
        this.execution_period = execution_period;
        this.task_status = task_status;
        this.files = files;
        this.description = description;
        this.deadline = deadline;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int execution_period;

    @Column(nullable = false)
    private int task_status;

    @Column(nullable = false)
    private String files;

    @Column(nullable = false)
    private String description;

    private LocalDate deadline;

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

    public int getExecution_period() {
        return execution_period;
    }
    public void setExecution_period(int execution_period) {
        this.execution_period = execution_period;
    }

    public int getTask_status() {
        return task_status;
    }
    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }

    public String getFiles() {
        return files;
    }
    public void setFiles(String files) {
        this.files = files;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", execution_period=" + execution_period +
                ", task_status=" + task_status +
                ", files='" + files + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
