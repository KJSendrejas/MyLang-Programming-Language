package main.java.mylang.semantic;

import java.util.*;

public class Scope {
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Scope parent;

    public Scope(Scope parent) { 
        this.parent = parent; 
    }

    public Scope getParent() {
        return parent;
    }

    public void define(Symbol s) {
        if (symbols.containsKey(s.name))
            throw new RuntimeException("Duplicate symbol: " + s.name);
        symbols.put(s.name, s);
    }

    public Symbol resolve(String name) {
        if (symbols.containsKey(name)) return symbols.get(name);
        return parent != null ? parent.resolve(name) : null;
    }
}