package com.usts.model;

public class Tuple {
    private Object name;
    private Object value;

    public Tuple() {
    }

    public Tuple(Object name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" +
                "name=" + name +
                ", value=" + value;
    }
}
