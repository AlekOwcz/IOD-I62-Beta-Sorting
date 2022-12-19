package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONInputObj {
    @JsonProperty("algorithms")
    private Object algorithms;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("attribute")
    private String attribute;
    public JSONInputObj(@JsonProperty("data") Object data, @JsonProperty("algorithms") Object algorithms, @JsonProperty("attribute") String attribute) {
        this.algorithms = algorithms;
        this.data = data;
        this.attribute = attribute;
    }

    public Object getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Object algorithms) {
        this.algorithms = algorithms;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getAttribute() { return attribute; }

    public void setAttribute(String attribute) { this.attribute=attribute; }
}