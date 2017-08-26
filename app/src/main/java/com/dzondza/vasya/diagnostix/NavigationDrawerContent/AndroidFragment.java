package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
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

        String versNameDescription = getString(R.string.android_version_name);
        recyclerViewLine.add(new RecyclerItemsData(versNameDescription, versionName));


        String versCode = getString(R.string.android_version_code);
        recyclerViewLine.add(new RecyclerItemsData(versCode, Build.VERSION.RELEASE));


        String apiLevel = getString(R.string.android_api_level);
        recyclerViewLine.add(new RecyclerItemsData(apiLevel, String.valueOf(Build.VERSION.SDK_INT)));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String securityPatchLevel = getString(R.string.android_security_patch_level);
            recyclerViewLine.add(new RecyclerItemsData(securityPatchLevel, Build.VERSION.SECURITY_PATCH));
        }


        String baseband = getString(R.string.android_baseband);
        recyclerViewLine.add(new RecyclerItemsData(baseband, Build.getRadioVersion()));


        String buildID = getString(R.string.android_build_id);
        recyclerViewLine.add(new RecyclerItemsData(buildID, Build.ID));


        String codeName = getString(R.string.android_codename);
        recyclerViewLine.add(new RecyclerItemsData(codeName, Build.VERSION.CODENAME));


        String fingerPrint = getString(R.string.android_fingerprint);
        recyclerViewLine.add(new RecyclerItemsData(fingerPrint, Build.FINGERPRINT));


        String incremental = getString(R.string.android_incremental);
        recyclerViewLine.add(new RecyclerItemsData(incremental, Build.VERSION.INCREMENTAL));


        String tags = getString(R.string.android_tags);
        recyclerViewLine.add(new RecyclerItemsData(tags, Build.TAGS));


        String type = getString(R.string.android_type);
        recyclerViewLine.add(new RecyclerItemsData(type, Build.TYPE));


        getActivity().setTitle(R.string.drawer_android);

        return view;
    }
}