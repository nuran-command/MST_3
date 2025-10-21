package com.carrental.algorithms;

import com.carrental.model.Edge;
import com.carrental.model.Graph;
import java.util.*;

public class PrimMST {
    public MSTResult run(Graph graph) {
        long operations = 0;
        long start = System.nanoTime();

        List<Edge> resultEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        String startNode = graph.getNodes().get(0);
        visited.add(startNode);

        for (Edge e : graph.getEdges()) {
            if (e.getFrom().equals(startNode) || e.getTo().equals(startNode)) pq.add(e);
        }

        while (!pq.isEmpty() && visited.size() < graph.vertexCount()) {
            Edge edge = pq.poll();
            operations++;

            String u = edge.getFrom(), v = edge.getTo();
            if (visited.contains(u) && visited.contains(v)) continue;

            resultEdges.add(edge);
            String newNode = visited.contains(u) ? v : u;
            visited.add(newNode);

            for (Edge e : graph.getEdges()) {
                if ((e.getFrom().equals(newNode) && !visited.contains(e.getTo())) ||
                        (e.getTo().equals(newNode) && !visited.contains(e.getFrom()))) {
                    pq.add(e);
                    operations++;
                }
            }
        }

        int totalCost = resultEdges.stream().mapToInt(Edge::getWeight).sum();
        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        return new MSTResult(resultEdges, totalCost, operations, timeMs);
    }
}