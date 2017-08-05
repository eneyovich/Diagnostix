package com.dzondza.vasya.diagnostix;

/**
 * model in MVC pattern
 */

public class RecyclerItemsData {
    protected String description;
    protected String solution;

    public RecyclerItemsData(String description, String solution) {
        this.description = description;
        this.solution = solution;
    }
}