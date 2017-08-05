package com.dzondza.vasya.diagnostix.MainContent;

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
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;


/**
 * contains information about display for each device
 */

public class DisplayFragment extends BaseDetailedFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // init recyclerView List
        initializeRecyclerView(view);


        Display display = getActivity().getWindowManager().getDefaultDisplay();


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.display_screen_settings), getString(R.string.open)));


        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);


        //screen resolution
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;

        String resolutionDescript = getString(R.string.resolution);
        String resolution = new StringBuilder().append(widthPixels)
                .append(" x ").append(heightPixels).toString();
        recyclerViewLine.add(new RecyclerItemsData(resolutionDescript, resolution));


        // pixel density
        String pixelDensityDescript = getString(R.string.display_pixel_density);
        String pixelDensity = String.valueOf(displayMetrics.densityDpi).concat(" dpi");
        recyclerViewLine.add(new RecyclerItemsData(pixelDensityDescript, pixelDensity));


        // xdpi/ydpi
        double xdpi = displayMetrics.xdpi;
        double ydpi = displayMetrics.ydpi;

        String xdpiYdpiDescript = getString(R.string.display_xdpi_ydpi);
        String xdpiYdpi = new StringBuilder().append((int)xdpi).append("/")
                .append((int)ydpi).append(" dpi").toString();
        recyclerViewLine.add(new RecyclerItemsData(xdpiYdpiDescript, xdpiYdpi));


        String technologyDescript = getString(R.string.display_technology);
        String renderer = GLES20.glGetString(GLES20.GL_RENDERER);
        String technology = new StringBuilder().append(renderer).append(" ")
                .append(GLES20.GL_VERSION).toString();
        recyclerViewLine.add(new RecyclerItemsData(technologyDescript, technology));


        //screen diagonal in inches
        double xInches = Math.pow(widthPixels/xdpi, 2);
        double yInches = Math.pow(heightPixels/ydpi, 2);
        double inches = Math.sqrt(xInches + yInches);
        double screenInches = 0.01 * Math.round(100 * inches);
        String diagonal = getString(R.string.display_screen_diagonal);
        recyclerViewLine.add(new RecyclerItemsData(diagonal, String.valueOf(screenInches)));


        // default orientation
        String orientationDescript = getString(R.string.display_orientation);
        String orientation;
        int rotation = display.getRotation();
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            orientation = getString(R.string.display_orientation_portrait);
        } else {
            orientation = getString(R.string.display_orientation_landscape);
        }
        recyclerViewLine.add(new RecyclerItemsData(orientationDescript, orientation));


        //display name
        String type = getString(R.string.display_type);
        recyclerViewLine.add(new RecyclerItemsData(type, display.getName()));


        // rate of this display in FPS
        String rateDescript = getString(R.string.display_rate);
        String rate = String.valueOf(display.getRefreshRate()).concat(" FPS");
        recyclerViewLine.add(new RecyclerItemsData(rateDescript, rate));


        ActivityManager activityManager = (ActivityManager) getActivity()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();


        String openGLVersion = getString(R.string.display_opengl_version);
        recyclerViewLine.add(new RecyclerItemsData(openGLVersion, configurationInfo.getGlEsVersion()));


        //toolbar title
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
}