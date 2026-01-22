package com.norbdev.server.dto;

import java.util.ArrayList;
import java.util.List;

public class GaussSeidelResponse {
    private double x;
    private double y;
    private int iterations;
    private List<double[]> path = new ArrayList<>();

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public int getIterations() { return iterations; }
    public void setIterations(int iterations) { this.iterations = iterations; }

    public List<double[]> getPath() { return path; }
    public void setPath(List<double[]> path) { this.path = path; }
}
