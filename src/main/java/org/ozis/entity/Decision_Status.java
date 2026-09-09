package org.ozis.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="desigion_status")
public class Decision_Status {

    public Decision_Status(String name) {
        this.name = name;
    }

    public Decision_Status() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "status")   // ← исправлено
    private List<Decision> decisions;

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

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<Decision> decisions) {
        this.decisions = decisions;
    }

    @Override
    public String toString() {
        return "Decision_Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}