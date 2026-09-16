package org.ozis.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "decision")
public class Decision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String files;

    @Column(nullable = false)
    private String context;

    @Column(name = "decision_text", nullable = false)
    private String decisionText;

    @Column(nullable = false)
    private String reason;

    // Статус решения
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_status", nullable = false)
    private Decision_Status status;

    // Проект
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // Зависимая задача
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependent_task")
    private Task dependentTask;

    // Альтернативное решение
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alternative_decision")
    private Decision alternativeDecision;

    // Решения, для которых данное решение является альтернативой
    @OneToMany(mappedBy = "alternativeDecision", fetch = FetchType.LAZY)
    private List<Decision> alternatives;


    public Integer getId() {
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


    public String getDecisionText() {
        return decisionText;
    }

    public void setDecisionText(String decisionText) {
        this.decisionText = decisionText;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public Decision_Status getStatus() {
        return status;
    }

    public void setStatus(Decision_Status status) {
        this.status = status;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public Task getDependentTask() {
        return dependentTask;
    }

    public void setDependentTask(Task dependentTask) {
        this.dependentTask = dependentTask;
    }


    public Decision getAlternativeDecision() {
        return alternativeDecision;
    }

    public void setAlternativeDecision(Decision alternativeDecision) {
        this.alternativeDecision = alternativeDecision;
    }


    public List<Decision> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Decision> alternatives) {
        this.alternatives = alternatives;
    }


    @Override
    public String toString() {
        return "Decision{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", files='" + files + '\'' +
                ", context='" + context + '\'' +
                ", decisionText='" + decisionText + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}