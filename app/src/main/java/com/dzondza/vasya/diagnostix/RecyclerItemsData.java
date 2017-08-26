package com.dzondza.vasya.diagnostix;

/**
 * data, which adds to recyclerView's item in list
 */

public class RecyclerItemsData {
    String mDescription;
    String mSolution;

    public RecyclerItemsData(String description, String solution) {
        this.mDescription = description;
        this.mSolution = solution;
    }
}