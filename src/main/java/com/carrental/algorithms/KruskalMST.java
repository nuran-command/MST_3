package com.carrental.algorithms;

import com.carrental.model.Edge;
import com.carrental.model.Graph;
import java.util.*;

public class KruskalMST {
    private Map<String, String> parent = new HashMap<>();
    private Map<String, Integer> rank = new HashMap<>();
    private long operations;

    private String find(String node) {
        operations++;
        if (!parent.get(node).equals(node))
            parent.put(node, find(parent.get(node)));
        return parent.get(node);
    }

    private void union(String u, String v) {
        operations++;
        String rootU = find(u);
        String rootV = find(v);
        if (!rootU.equals(rootV)) {
            if (rank.get(rootU) < rank.get(rootV)) parent.put(rootU, rootV);
            else if (rank.get(rootU) > rank.get(rootV)) parent.put(rootV, rootU);
            else {
                parent.put(rootV, rootU);
                rank.put(rootU, rank.get(rootU) + 1);
            }
        }
    }

    public MSTResult run(Graph graph) {
        operations = 0;
        long start = System.nanoTime();

        for (String node : graph.getNodes()) {
            parent.put(node, node);
            rank.put(node, 0);
        }

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        List<Edge> resultEdges = new ArrayList<>();

        for (Edge e : edges) {
            String rootU = find(e.getFrom());
            String rootV = find(e.getTo());
            if (!rootU.equals(rootV)) {
                resultEdges.add(e);
                union(rootU, rootV);
            }
            operations++;
        }

        int totalCost = resultEdges.stream().mapToInt(Edge::getWeight).sum();
        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        return new MSTResult(resultEdges, totalCost, operations, timeMs);
    }
}
