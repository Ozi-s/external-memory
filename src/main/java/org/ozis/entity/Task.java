package org.ozis.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="table")
public class Task {

    public Task() {
    }

    public Task(String name, int execution_period, Task_Status status, String files, String description, LocalDate deadline, Project project) {
        this.name = name;
        this.execution_period = execution_period;
        this.status = status;
        this.files = files;
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int execution_period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_status")
    private Task_Status status;

    @Column(nullable = false)
    private String files;

    @Column(nullable = false)
    private String description;

    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;

    @OneToMany(mappedBy = "dependent_task")
    private List<Desigion> desigions;

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

    public List<Desigion> getDesigions() {
        return desigions;
    }
    public void setDesigions(List<Desigion> desigions) {
        this.desigions = desigions;
    }

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    public Task_Status getStatus() {
        return status;
    }
    public void setStatus(Task_Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", execution_period=" + execution_period +
                ", status=" + status +
                ", files='" + files + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", project=" + project +
                '}';
    }
}
