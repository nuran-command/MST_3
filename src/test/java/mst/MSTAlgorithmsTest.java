package mst;

import com.carrental.algorithms.*;
import com.carrental.model.*;
import com.carrental.utils.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Automated tests for Prim’s and Kruskal’s MST algorithms.
 * Covers correctness, performance, and reproducibility.
 */
public class MSTAlgorithmsTest {

    private PrimMST prim;
    private KruskalMST kruskal;

    @BeforeEach
    void setup() {
        prim = new PrimMST();
        kruskal = new KruskalMST();
    }

    // --- Utility method to check if MST connects all vertices ---
    private boolean isConnected(Graph g, List<Edge> edges) {
        Set<String> visited = new HashSet<>();
        if (edges.isEmpty()) return g.getNodes().size() <= 1;

        Map<String, List<String>> adj = new HashMap<>();
        for (Edge e : edges) {
            adj.computeIfAbsent(e.getFrom(), k -> new ArrayList<>()).add(e.getTo());
            adj.computeIfAbsent(e.getTo(), k -> new ArrayList<>()).add(e.getFrom());
        }

        Deque<String> stack = new ArrayDeque<>();
        String start = edges.get(0).getFrom();
        stack.push(start);

        while (!stack.isEmpty()) {
            String node = stack.pop();
            if (!visited.add(node)) continue;
            if (adj.containsKey(node)) {
                for (String n : adj.get(node)) {
                    if (!visited.contains(n)) stack.push(n);
                }
            }
        }
        return visited.containsAll(g.getNodes());
    }

    // --- Utility method to detect cycle in MST edges ---
    private boolean hasCycle(Graph g, List<Edge> edges) {
        Map<String, String> parent = new HashMap<>();
        for (String node : g.getNodes()) parent.put(node, node);

        for (Edge e : edges) {
            String root1 = find(parent, e.getFrom());
            String root2 = find(parent, e.getTo());
            if (root1.equals(root2)) return true;
            parent.put(root1, root2);
        }
        return false;
    }

    private String find(Map<String, String> parent, String v) {
        if (parent.get(v).equals(v)) return v;
        String root = find(parent, parent.get(v));
        parent.put(v, root);
        return root;
    }

    // --- Dataset Loader ---
    private List<Graph> loadDataset(String path) {
        return JSONReader.loadGraphs(path);
    }

    // --- 1. Correctness Tests ---

    @Test
    void testSmallGraphCorrectness() {
        List<Graph> graphs = loadDataset("data/input.json");
        for (Graph g : graphs) {
            MSTResult primRes = prim.run(g);
            MSTResult kruskalRes = kruskal.run(g);

            assertEquals(primRes.getTotalCost(), kruskalRes.getTotalCost(), "Total MST cost must match");
            assertEquals(g.getNodes().size() - 1, primRes.getMstEdges().size(), "Prim edges count mismatch");
            assertEquals(g.getNodes().size() - 1, kruskalRes.getMstEdges().size(), "Kruskal edges count mismatch");

            assertFalse(hasCycle(g, primRes.getMstEdges()), "Prim MST must be acyclic");
            assertFalse(hasCycle(g, kruskalRes.getMstEdges()), "Kruskal MST must be acyclic");

            assertTrue(isConnected(g, primRes.getMstEdges()), "Prim MST must connect all vertices");
            assertTrue(isConnected(g, kruskalRes.getMstEdges()), "Kruskal MST must connect all vertices");
        }
    }

    @Test
    void testDisconnectedGraph() {
        Graph g = new Graph(99);
        g.addEdge("A", "B", 2);
        g.addEdge("C", "D", 3); // separate component

        MSTResult primRes = prim.run(g);
        MSTResult kruskalRes = kruskal.run(g);

        assertTrue(primRes.getMstEdges().isEmpty() || !isConnected(g, primRes.getMstEdges()),
                "Prim should handle disconnected graph");
        assertTrue(kruskalRes.getMstEdges().isEmpty() || !isConnected(g, kruskalRes.getMstEdges()),
                "Kruskal should handle disconnected graph");
    }

    // --- 2. Performance & Consistency Tests ---

    @Test
    void testExecutionTimeAndOperationsNonNegative() {
        List<Graph> graphs = loadDataset("data/input.json");
        for (Graph g : graphs) {
            MSTResult primRes = prim.run(g);
            MSTResult kruskalRes = kruskal.run(g);

            assertTrue(primRes.getExecutionTimeMs() >= 0);
            assertTrue(kruskalRes.getExecutionTimeMs() >= 0);
            assertTrue(primRes.getOperationsCount() >= 0);
            assertTrue(kruskalRes.getOperationsCount() >= 0);
        }
    }

    @Test
    void testReproducibility() {
        List<Graph> graphs = loadDataset("data/input.json");
        for (Graph g : graphs) {
            MSTResult res1 = prim.run(g);
            MSTResult res2 = prim.run(g);
            assertEquals(res1.getTotalCost(), res2.getTotalCost(), "Prim results should be reproducible");

            MSTResult res3 = kruskal.run(g);
            MSTResult res4 = kruskal.run(g);
            assertEquals(res3.getTotalCost(), res4.getTotalCost(), "Kruskal results should be reproducible");
        }
    }

    // --- 3. Large Graph Scalability Test (synthetic) ---
    @Test
    void testLargeGraphPerformance() {
        Graph g = new Graph(500);
        for (int i = 1; i <= 30; i++) {
            for (int j = i + 1; j <= 30; j++) {
                g.addEdge("V" + i, "V" + j, (i + j) % 10 + 1);
            }
        }
        MSTResult primRes = prim.run(g);
        MSTResult kruskalRes = kruskal.run(g);

        assertEquals(primRes.getTotalCost(), kruskalRes.getTotalCost());
        assertEquals(g.getNodes().size() - 1, primRes.getMstEdges().size());
    }
}