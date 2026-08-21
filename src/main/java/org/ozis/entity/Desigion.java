package org.ozis.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "desigion")
public class Desigion {

    public Desigion(String name, String files, String context, String desigion_text, String reason, Desigion_Status status, Project project, Task task, Desigion alternative_desigion, int dependent_task) {
        this.name = name;
        this.files = files;
        this.context = context;
        this.desigion_text = desigion_text;
        this.reason = reason;
        this.status = status;
        this.project = project;
        this.task = task;
        this.alternative_desigion = alternative_desigion;
        this.dependent_task = dependent_task;
    }

    public Desigion() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String files;

    @Column(nullable = false)
    private String context;

    @Column(nullable = false)
    private String desigion_text;

    @Column(nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desigion_status")
    private Desigion_Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dependent_task")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="alternative_desigion")
    private Desigion alternative_desigion;

    @OneToMany(mappedBy = "alternative_desigion")
    private List<Desigion> alternatives;

    private int dependent_task;

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

    public String getFiles() {
        return files;
    }
    public void setFiles(String files) {
        this.files = files;
    }

    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }

    public String getDesigion_text() {
        return desigion_text;
    }
    public void setDesigion_text(String desigion_text) {
        this.desigion_text = desigion_text;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getDependent_task() {
        return dependent_task;
    }
    public void setDependent_task(int dependent_task) {
        this.dependent_task = dependent_task;
    }

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    public Desigion_Status getStatus() {
        return status;
    }
    public void setStatus(Desigion_Status status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    public Desigion getAlternative_desigion() {
        return alternative_desigion;
    }
    public void setAlternative_desigion(Desigion alternative_desigion) {
        this.alternative_desigion = alternative_desigion;
    }

    public List<Desigion> getAlternatives() {
        return alternatives;
    }
    public void setAlternatives(List<Desigion> alternatives) {
        this.alternatives = alternatives;
    }

    @Override
    public String toString() {
        return "Desigion{" +
                "name='" + name + '\'' +
                ", files='" + files + '\'' +
                ", context='" + context + '\'' +
                ", desigion_text='" + desigion_text + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", project=" + project +
                ", task=" + task +
                ", alternative_desigion=" + alternative_desigion +
                ", dependent_task=" + dependent_task +
                '}';
    }
}
