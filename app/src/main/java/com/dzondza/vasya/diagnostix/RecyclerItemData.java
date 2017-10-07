package com.dzondza.vasya.diagnostix;

import android.graphics.drawable.Drawable;

/**
 * data, which adds to recyclerView's item in list
 */

public class RecyclerItemData {
    private Drawable mAppImage;
    private String mDescription;
    private String mSolution;

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


    public Drawable getAppImage() {
        return mAppImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getSolution() {
        return mSolution;
    }
}