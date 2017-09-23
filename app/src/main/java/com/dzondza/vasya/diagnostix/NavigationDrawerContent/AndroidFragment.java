package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dzondza.vasya.diagnostix.RecyclerItemData;
import com.dzondza.vasya.diagnostix.R;

/**
 * contains information about android system for each device
 */

public class AndroidFragment extends BaseDetailedFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // activates recyclerView
        initializeRecyclerView(view);

        recyclerListData();

        getActivity().setTitle(R.string.drawer_android);

        return view;
    }


    @Override
    protected void recyclerListData() {
        String versionName;
        switch (Build.VERSION.SDK_INT) {
            case 19: versionName = getString(R.string.kitkat);
                break;
            case 21:
            case 22: versionName = getString(R.string.lollipop);
                break;
            case 23: versionName = getString(R.string.marshmallow);
                break;
            case 24:
            case 25: versionName = getString(R.string.nougat);
                break;
            case 26: versionName = getString(R.string.oreo);
                break;
            default: versionName = getString(R.string.jelly_bean);
        }

        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_version_name),
                versionName));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_version_code),
                Build.VERSION.RELEASE));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_api_level),
                String.valueOf(Build.VERSION.SDK_INT)));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_security_patch_level),
                    Build.VERSION.SECURITY_PATCH));
        }


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_baseband),
                Build.getRadioVersion()));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_build_id), Build.ID));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_codename),
                Build.VERSION.CODENAME));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_fingerprint),
                Build.FINGERPRINT));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_incremental),
                Build.VERSION.INCREMENTAL));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_tags), Build.TAGS));


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.android_type), Build.TYPE));
    }
}