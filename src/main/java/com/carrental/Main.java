package com.carrental;

import com.carrental.model.Graph;
import com.carrental.algorithms.*;
import com.carrental.utils.*;
import org.json.JSONObject;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputPath = "data/input.json";
        String outputPath = "data/output.json";

        List<Graph> graphs = JSONReader.loadGraphs(inputPath);
        List<JSONObject> results = new ArrayList<>();

        PrimMST prim = new PrimMST();
        KruskalMST kruskal = new KruskalMST();

        for (Graph g : graphs) {
            MSTResult primRes = prim.run(g);
            MSTResult kruskalRes = kruskal.run(g);

            JSONObject graphResult = JSONWriter.createGraphResult(g, primRes, kruskalRes);
            results.add(graphResult);
        }

        JSONWriter.writeResults(outputPath, results);
        System.out.println("MST results written to " + outputPath);
    }
}