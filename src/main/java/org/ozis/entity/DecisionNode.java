package org.ozis.entity;

import java.util.List;
import java.util.ArrayList;

public class DecisionNode {

    private String name;
    private List<DecisionNode> children;

    public DecisionNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }
}

