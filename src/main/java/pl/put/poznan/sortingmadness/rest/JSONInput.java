package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONInput {
    @JsonProperty("algorithms")
    private Object algorithms;

    @JsonProperty("data")
    private Object data;

    public JSONInput(@JsonProperty("data") Object data, @JsonProperty("algorithms") Object algorithms) {
        this.algorithms = algorithms;
        this.data = data;
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
}