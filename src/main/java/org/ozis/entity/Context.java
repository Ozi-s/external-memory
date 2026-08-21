package org.ozis.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="context")
public class Context {

    public Context() {
    }

    public Context(String difficulty, LocalDateTime last_updated, String think, String in_progress, String done, String to_do, Project project) {
        this.difficulty = difficulty;
        this.last_updated = last_updated;
        this.think = think;
        this.in_progress = in_progress;
        this.done = done;
        this.to_do = to_do;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String difficulty;

    private LocalDateTime last_updated;

    private String think;

    private String in_progress;

    private String done;

    private String to_do;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getLast_updated() {
        return last_updated;
    }
    public void setLast_updated(LocalDateTime last_updated) {
        this.last_updated = last_updated;
    }

    public String getThink() {
        return think;
    }
    public void setThink(String think) {
        this.think = think;
    }

    public String getIn_progress() {
        return in_progress;
    }
    public void setIn_progress(String in_progress) {
        this.in_progress = in_progress;
    }

    public String getDone() {
        return done;
    }
    public void setDone(String done) {
        this.done = done;
    }

    public String getTo_do() {
        return to_do;
    }
    public void setTo_do(String to_do) {
        this.to_do = to_do;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Context{" +
                "difficulty='" + difficulty + '\'' +
                ", last_updated=" + last_updated +
                ", think='" + think + '\'' +
                ", in_progress='" + in_progress + '\'' +
                ", done='" + done + '\'' +
                ", to_do='" + to_do + '\'' +
                ", project=" + project +
                '}';
    }
}
