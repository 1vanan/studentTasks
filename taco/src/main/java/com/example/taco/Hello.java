package com.example.taco;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

public class Hello {
    private String name;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    private List<String> names;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
