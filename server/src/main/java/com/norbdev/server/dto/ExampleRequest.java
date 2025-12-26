package com.norbdev.server.dto;

import java.util.function.BiFunction;

public class ExampleRequest {
    String equation;
    String mode;
    int a;
    int b;
    int epsilon;

    public ExampleRequest(String equation, String mode, int a, int b, int epsilon) {
        this.equation = equation;
        this.mode = mode;
        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(int epsilon) {
        this.epsilon = epsilon;
    }
}
