package pl.put.poznan.sortingmadness.logic;

import java.util.List;

public class FormInput {
    List<String> methods;
    String order;
    String data;
    String type;
    String attribute;

    public List<String> getMethods() {
        return methods;
    }
    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
