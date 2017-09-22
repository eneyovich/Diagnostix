package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;
import java.io.File;


/**
 * contains system values like manufacturer, memory, etc.
 */

public class DeviceFragment extends BaseDetailedFragment {

    @SuppressLint("HardwareIds")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        //activates recyclerView
        initializeRecyclerView(view);

        recyclerListData();

        getActivity().setTitle(R.string.drawer_device);

        return view;
    }


    //gets internal storage's free space in MB
    private String freeInternalSpace() {
        long blockSize, availableBlocks;

        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSize();
            availableBlocks = statFs.getAvailableBlocks();
        } else {
            blockSize = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        }

        return String.valueOf(blockSize*availableBlocks/1024/1024);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        if (position >= 12) {
            try {
                startActivity(new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
            } catch (Exception e) {
                Toast.makeText(getActivity(), getString(R.string.option_unavailable), Toast.LENGTH_SHORT).show();
            }
        }
        super.onItemClick(adapterView, view, position, l);
    }


    @Override
    protected void recyclerListData() {

        String model = new StringBuilder(Build.BRAND).append(" ").append(Build.MODEL).toString();
        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_dev_model), model));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_manufacturer),
                Build.MANUFACTURER));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_model), Build.MODEL));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_brand), Build.BRAND));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_board), Build.BOARD));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_device), Build.DEVICE));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_hardware), Build.HARDWARE));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_product), Build.PRODUCT));


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_serial), Build.SERIAL));

        //           Memory values           //

        StatFs statFs = new StatFs(Environment.getDataDirectory().toString());

        ActivityManager activityManager = (ActivityManager) getActivity()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);


        //total ram memory
        String ramTotal = String.valueOf(memoryInfo.totalMem/1024/1024).concat(" Mb");
        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_total_ram), ramTotal));


        //available ram memory
        long ramPercent = Math.round(memoryInfo.availMem/(double)memoryInfo.totalMem*100);
        String ramAvailable = new StringBuilder().append(memoryInfo.availMem/1024/1024)
                .append(" Mb (").append(ramPercent).append( " %)").toString();
        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.device_available_ram), ramAvailable));


        //Internal storage's free space in MB
        String freeInternalSpace = getString(R.string.device_internal_free_space);
        recyclerViewLine.add(new RecyclerItemsData(freeInternalSpace, freeInternalSpace().concat(" Mb")));


        //Internal storage's total space in MB
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String totalInternalSpace = getString(R.string.device_internal_total_space);
            String totalInternalSpaceSolution = new StringBuilder()
                    .append(statFs.getTotalBytes()/1024/1024).append(" Mb").toString();
            recyclerViewLine.add(new RecyclerItemsData(totalInternalSpace, totalInternalSpaceSolution));
        }

        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.internal_storage_settings), getString(R.string.open)));
    }
}