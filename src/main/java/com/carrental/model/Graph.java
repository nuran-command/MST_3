package com.carrental.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    public Graph(int id) {
        this.id = id;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public int getId() { return id; }
    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }

    public void addNode(String node) {
        if (!nodes.contains(node)) nodes.add(node);
    }

    public void addEdge(String from, String to, int weight) {
        edges.add(new Edge(from, to, weight));
        addNode(from);
        addNode(to);
    }

    public int vertexCount() { return nodes.size(); }
    public int edgeCount() { return edges.size(); }
}