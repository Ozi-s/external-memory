package org.ozis.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="desigion_status")
public class Desigion_Status {

    public Desigion_Status(String name) {
        this.name = name;
    }

    public Desigion_Status() {
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

    public List<Decision> getDesigions() {
        return decisions;
    }

    public void setDesigions(List<Decision> decisions) {
        this.decisions = decisions;
    }

    @Override
    public String toString() {
        return "Desigion_Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}