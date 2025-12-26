package com.norbdev.server.dto;

public class ResultResponse {
    String equation;
    int result;
    int epsilon;

    public ResultResponse(String equation, int result, int epsilon) {
        this.equation = equation;
        this.result = result;
        this.epsilon = epsilon;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(int epsilon) {
        this.epsilon = epsilon;
    }
}
