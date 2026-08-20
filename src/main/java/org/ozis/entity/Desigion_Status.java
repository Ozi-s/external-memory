package org.ozis.entity;

import jakarta.persistence.*;

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
    private int id;

    @Column(nullable = false)
    private String name;

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

    @Override
    public String toString() {
        return "Desigion_Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
