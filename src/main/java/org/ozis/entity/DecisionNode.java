package org.ozis.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decision_node")
public class DecisionNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128)
    private String name;

    // Родительский узел
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private DecisionNode parent;

    // Дочерние узлы
    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DecisionNode> children = new ArrayList<>();

    // Если узел представляет конкретное решение
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Decision decision;

    // Проект, которому принадлежит дерево
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    public DecisionNode() {
    }


    public DecisionNode(String name) {
        this.name = name;
    }


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


    public DecisionNode getParent() {
        return parent;
    }

    public void setParent(DecisionNode parent) {
        this.parent = parent;
    }


    public List<DecisionNode> getChildren() {
        return children;
    }

    public void setChildren(List<DecisionNode> children) {
        this.children = children;
    }


    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    @Override
    public String toString() {
        return "DecisionNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}