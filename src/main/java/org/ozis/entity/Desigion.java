package org.ozis.entity;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "desigion")
public class Desigion {

    public Desigion(String name, String files, String context, int alternative_desigion, String desigion_text, String reason, int desigion_status, int dependent_task) {
        this.name = name;
        this.files = files;
        this.context = context;
        this.alternative_desigion = alternative_desigion;
        this.desigion_text = desigion_text;
        this.reason = reason;
        this.desigion_status = desigion_status;
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

    private int alternative_desigion;

    @Column(nullable = false)
    private String desigion_text;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private int desigion_status;

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

    public int getAlternative_desigion() {
        return alternative_desigion;
    }
    public void setAlternative_desigion(int alternative_desigion) {
        this.alternative_desigion = alternative_desigion;
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

    public int getDesigion_status() {
        return desigion_status;
    }
    public void setDesigion_status(int desigion_status) {
        this.desigion_status = desigion_status;
    }

    public int getDependent_task() {
        return dependent_task;
    }
    public void setDependent_task(int dependent_task) {
        this.dependent_task = dependent_task;
    }

    @Override
    public String toString() {
        return "Desigion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", files='" + files + '\'' +
                ", context='" + context + '\'' +
                ", alternative_desigion=" + alternative_desigion +
                ", desigion_text='" + desigion_text + '\'' +
                ", reason='" + reason + '\'' +
                ", desigion_status=" + desigion_status +
                ", dependent_task=" + dependent_task +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Desigion desigion = (Desigion) o;
        return id == desigion.id && alternative_desigion == desigion.alternative_desigion && desigion_status == desigion.desigion_status && dependent_task == desigion.dependent_task && Objects.equals(name, desigion.name) && Objects.equals(files, desigion.files) && Objects.equals(context, desigion.context) && Objects.equals(desigion_text, desigion.desigion_text) && Objects.equals(reason, desigion.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, files, context, alternative_desigion, desigion_text, reason, desigion_status, dependent_task);
    }
}
