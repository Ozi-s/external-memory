package org.ozis.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="context")
public class Context {

    public Context() {
    }

    public Context(String difficulty, LocalDateTime last_updated, String think, String in_progress, String done, String to_do) {
        this.difficulty = difficulty;
        this.last_updated = last_updated;
        this.think = think;
        this.in_progress = in_progress;
        this.done = done;
        this.to_do = to_do;
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

    @Override
    public String toString() {
        return "Context{" +
                "last_updated=" + last_updated +
                ", think='" + think + '\'' +
                ", in_progress='" + in_progress + '\'' +
                ", done='" + done + '\'' +
                ", to_do='" + to_do + '\'' +
                ", id=" + id +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Context context = (Context) o;
        return id == context.id && Objects.equals(difficulty, context.difficulty) && Objects.equals(last_updated, context.last_updated) && Objects.equals(think, context.think) && Objects.equals(in_progress, context.in_progress) && Objects.equals(done, context.done) && Objects.equals(to_do, context.to_do);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, difficulty, last_updated, think, in_progress, done, to_do);
    }
}
