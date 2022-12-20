package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONOutputObj {
    @JsonProperty("algorithms")
    private Object algorithms;

    @JsonProperty("sortedData")
    private Object sortedData;

    @JsonProperty("attribute")
    private String attribute;

    @JsonProperty("times")
    private Object times;
    public JSONOutputObj(@JsonProperty("algorithms") Object algorithms,
                        @JsonProperty("sortedData") Object sortedData,
                        @JsonProperty("attribute") String attribute,
                        @JsonProperty("times") Object times) {
        this.algorithms = algorithms;
        this.sortedData = sortedData;
        this.attribute = attribute;
        this.times = times;
    }



    public Object getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Object algorithms) {
        this.algorithms = algorithms;
    }

    public Object getSortedDataData() {
        return sortedData;
    }

    public void setSortedData(Object data) {
        this.sortedData = data;
    }

    public String getAttribute() { return attribute; }

    public void setAttribute(String attribute) { this.attribute=attribute; }

    public Object getTimes() { return times; }

    public void setTimes(Object times) { this.times = times; }
}