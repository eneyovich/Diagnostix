package com.dzondza.vasya.diagnostix;

import android.graphics.drawable.Drawable;

/**
 * data, which adds to recyclerView's item in list
 */

public class RecyclerItemData {
    Drawable mAppImage;
    String mDescription;
    String mSolution;

    //constructor to make list, using RecyclerAdapter
    public RecyclerItemData(String description, String solution) {
        this.mDescription = description;
        this.mSolution = solution;
    }

    //constructor to make list, using RecyclerAdapterInstalledApps
    public RecyclerItemData(Drawable appImage, String description, String solution) {
        this.mAppImage = appImage;
        this.mDescription = description;
        this.mSolution = solution;
    }
}