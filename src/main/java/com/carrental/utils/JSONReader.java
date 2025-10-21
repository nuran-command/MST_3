package com.carrental.utils;

import com.carrental.model.Edge;
import com.carrental.model.Graph;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JSONReader {
    public static List<Graph> loadGraphs(String path) {
        List<Graph> graphs = new ArrayList<>();
        try {
            String content = Files.readString(Paths.get(path));
            JSONObject root = new JSONObject(content);
            JSONArray graphArray = root.getJSONArray("graphs");

            for (int i = 0; i < graphArray.length(); i++) {
                JSONObject g = graphArray.getJSONObject(i);
                Graph graph = new Graph(g.getInt("id"));
                JSONArray edges = g.getJSONArray("edges");

                for (int j = 0; j < edges.length(); j++) {
                    JSONObject e = edges.getJSONObject(j);
                    graph.addEdge(e.getString("from"), e.getString("to"), e.getInt("weight"));
                }
                graphs.add(graph);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graphs;
    }
}