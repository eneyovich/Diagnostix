package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.dzondza.vasya.diagnostix.RecyclerItemData;
import com.dzondza.vasya.diagnostix.R;


/**
 * contains information about display
 */

public class DisplayFragment extends BaseDetailedFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        //activates recyclerView
        initializeRecyclerView(view);

        recyclerListData();

        getActivity().setTitle(R.string.drawer_display);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == 0) {
            try {
                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            } catch (Exception e) {
                Toast.makeText(getActivity(), getString(R.string.option_unavailable), Toast.LENGTH_SHORT).show();
            }
        }
        super.onItemClick(adapterView, view, position, l);
    }


    @Override
    protected void recyclerListData() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();


        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_screen_settings), getString(R.string.open)));


        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);

        //display's resolution
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;


        String resolution = new StringBuilder().append(widthPixels)
                .append(" x ").append(heightPixels).toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.resolution), resolution));

        // pixel density
        String pixelDensity = String.valueOf(displayMetrics.densityDpi).concat(" dpi");
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_pixel_density),
                pixelDensity));

        // xdpi/ydpi
        double xdpi = displayMetrics.xdpi;
        double ydpi = displayMetrics.ydpi;


        String xdpiYdpi = new StringBuilder().append((int)xdpi).append("/")
                .append((int)ydpi).append(" dpi").toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_xdpi_ydpi), xdpiYdpi));


        String renderer = GLES20.glGetString(GLES20.GL_RENDERER);
        String technology = new StringBuilder().append(renderer).append(" ")
                .append(GLES20.GL_VERSION).toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_technology), technology));


        //display's diagonal in inches
        double xInches = Math.pow(widthPixels/xdpi, 2);
        double yInches = Math.pow(heightPixels/ydpi, 2);
        double inches = Math.sqrt(xInches + yInches);
        double screenInches = 0.01 * Math.round(100 * inches);
        String diagonal = getString(R.string.display_screen_diagonal);
        recyclerViewLine.add(new RecyclerItemData(diagonal, String.valueOf(screenInches)));


        // default display's orientation
        String orientation;
        int rotation = display.getRotation();
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            orientation = getString(R.string.display_orientation_portrait);
        } else {
            orientation = getString(R.string.display_orientation_landscape);
        }
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_orientation), orientation));


        //display's name
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_type), display.getName()));


        // display's rate in FPS
        String rate = String.valueOf(display.getRefreshRate()).concat(" FPS");
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.display_rate), rate));


        ActivityManager activityManager = (ActivityManager) getActivity()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();


        String openGLVersion = getString(R.string.display_opengl_version);
        recyclerViewLine.add(new RecyclerItemData(openGLVersion, configurationInfo.getGlEsVersion()));
    }
}