package main.java.mylang.semantic;

import java.util.*;

import java.util.List;

public class Symbol {
    public final String name;
    public final Type type;
    public final List<Type> params; // null if variable

    public Symbol(String name, Type type) {
        this(name, type, null);
    }

    public Symbol(String name, Type type, List<Type> params) {
        this.name = name;
        this.type = type;
        this.params = params;
    }

    public boolean isFunction() {
        return params != null;
    }
}