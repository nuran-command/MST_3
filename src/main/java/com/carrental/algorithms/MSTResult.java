package com.carrental.algorithms;

import com.carrental.model.Edge;
import java.util.List;

public class MSTResult {
    public List<Edge> mstEdges;
    public int totalCost;
    public long operationsCount;
    public double executionTimeMs;

    public MSTResult(List<Edge> mstEdges, int totalCost, long operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }
}