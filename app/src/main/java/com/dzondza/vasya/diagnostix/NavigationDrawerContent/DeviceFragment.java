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


        String model = getString(R.string.device_model);
        recyclerViewLine.add(new RecyclerItemsData(model, new StringBuilder(Build.BRAND)
                .append(" ").append(Build.MODEL).toString()));


        String manufacturer = getString(R.string.device_manufacturer);
        recyclerViewLine.add(new RecyclerItemsData(manufacturer, Build.MANUFACTURER));


        String modelDetailed = getString(R.string.device_model_detal);
        recyclerViewLine.add(new RecyclerItemsData(modelDetailed, Build.MODEL));


        String brand = getString(R.string.device_brand);
        recyclerViewLine.add(new RecyclerItemsData(brand, Build.BRAND));


        String board = getString(R.string.device_board);
        recyclerViewLine.add(new RecyclerItemsData(board, Build.BOARD));


        String device = getString(R.string.device_device);
        recyclerViewLine.add(new RecyclerItemsData(device, Build.DEVICE));


        String hardware = getString(R.string.device_hardware);
        recyclerViewLine.add(new RecyclerItemsData(hardware, Build.HARDWARE));


        String product = getString(R.string.device_product);
        recyclerViewLine.add(new RecyclerItemsData(product, Build.PRODUCT));


        String serial = getString(R.string.device_serial);
        recyclerViewLine.add(new RecyclerItemsData(serial, Build.SERIAL));


        //           Memory values           //

        StatFs statFs = new StatFs(Environment.getDataDirectory().toString());

        ActivityManager activityManager = (ActivityManager) getActivity()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);


        //total ram memory
        String ramTotal = getString(R.string.device_total_ram);
        String ramTotalSolution = String.valueOf(memoryInfo.totalMem/1024/1024).concat(" Mb");
        recyclerViewLine.add(new RecyclerItemsData(ramTotal, ramTotalSolution));


        //available ram memory
        String ramAvailableDescript = getString(R.string.device_available_ram);
        long ramPercent = Math.round(memoryInfo.availMem/(double)memoryInfo.totalMem*100);
        String ramAvailable = new StringBuilder().append(memoryInfo.availMem/1024/1024)
                .append(" Mb (").append(ramPercent).append( " %)").toString();
        recyclerViewLine.add(new RecyclerItemsData(ramAvailableDescript, ramAvailable));


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

        if (position == 13) {
            try {
                startActivity(new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
            } catch (Exception e) {
                Toast.makeText(getActivity(), getString(R.string.option_unavailable), Toast.LENGTH_SHORT).show();
            }
        }
        super.onItemClick(adapterView, view, position, l);
    }
}