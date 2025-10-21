package com.carrental.utils;

import com.carrental.model.Edge;
import com.carrental.algorithms.MSTResult;
import com.carrental.model.Graph;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.util.List;

public class JSONWriter {
    public static void writeResults(String path, List<JSONObject> results) {
        JSONObject root = new JSONObject();
        root.put("results", results);
        try (FileWriter file = new FileWriter(path)) {
            file.write(root.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject createGraphResult(Graph g, MSTResult prim, MSTResult kruskal) {
        JSONObject obj = new JSONObject();
        obj.put("graph_id", g.getId());

        JSONObject stats = new JSONObject();
        stats.put("vertices", g.vertexCount());
        stats.put("edges", g.edgeCount());
        obj.put("input_stats", stats);

        obj.put("prim", mstToJson(prim));
        obj.put("kruskal", mstToJson(kruskal));

        return obj;
    }

    private static JSONObject mstToJson(MSTResult r) {
        JSONObject o = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Edge e : r.mstEdges) {
            JSONObject eObj = new JSONObject();
            eObj.put("from", e.getFrom());
            eObj.put("to", e.getTo());
            eObj.put("weight", e.getWeight());
            arr.put(eObj);
        }
        o.put("mst_edges", arr);
        o.put("total_cost", r.totalCost);
        o.put("operations_count", r.operationsCount);
        o.put("execution_time_ms", r.executionTimeMs);
        return o;
    }
}