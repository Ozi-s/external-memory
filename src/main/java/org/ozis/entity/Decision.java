package org.ozis.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "desigion")
public class Decision {

    public Decision(String name, String files, String context, String decision_text, String reason, Decision_Status status, Project project_id, Task dependent_task, Decision alternative_decision) {
        this.name = name;
        this.files = files;
        this.context = context;
        this.desigion_text = decision_text;
        this.reason = reason;
        this.status = status;
        this.project_id = project_id;
        this.dependent_task = dependent_task;
        this.alternative_desigion = alternative_decision;
    }

    public Decision() {
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
    private Decision_Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dependent_task")
    private Task dependent_task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="alternative_desigion")
    private Decision alternative_desigion;

    @OneToMany(mappedBy = "alternative_desigion")
    private List<Decision> alternatives;

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

    public String getDecision_text() {
        return desigion_text;
    }
    public void setDecision_text(String decision_text) {
        this.desigion_text = decision_text;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDependent_task(Task dependent_task) {
        this.dependent_task = dependent_task;
    }

    public Project getProject_id() {
        return project_id;
    }
    public void setProject_id(Project project_id) {
        this.project_id = project_id;
    }

    public Decision_Status getStatus() {
        return status;
    }
    public void setStatus(Decision_Status status) {
        this.status = status;
    }

    public Decision getAlternative_decision() {
        return alternative_desigion;
    }
    public void setAlternative_decision(Decision alternative_decision) {
        this.alternative_desigion = alternative_decision;
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
                "name='" + name + '\'' +
                ", files='" + files + '\'' +
                ", context='" + context + '\'' +
                ", decision_text='" + desigion_text + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", project=" + project_id +
                ", alternative_decision=" + alternative_desigion +
                ", dependent_task=" + dependent_task +
                '}';
    }
}
