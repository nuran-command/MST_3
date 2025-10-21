package com.carrental.model;

import java.util.List;
import com.carrental.model.Edge;

public class MSTResult {
    private List<Edge> mstEdges;
    private double totalCost;
    private int operationsCount;
    private double executionTimeMs;

    public MSTResult(List<Edge> mstEdges, double totalCost, int operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }

    // --- Getters used by tests ---
    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    // --- Setters (optional) ---
    public void setMstEdges(List<Edge> mstEdges) {
        this.mstEdges = mstEdges;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setOperationsCount(int operationsCount) {
        this.operationsCount = operationsCount;
    }

    public void setExecutionTimeMs(double executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}