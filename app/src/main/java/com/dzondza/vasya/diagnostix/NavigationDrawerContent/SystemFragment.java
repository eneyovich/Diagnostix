package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dzondza.vasya.diagnostix.RecyclerItemData;
import com.dzondza.vasya.diagnostix.R;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * contains information about system, cpu, etc.
 */

public class SystemFragment extends BaseDetailedFragment {
    private Handler mHandler = new Handler();
    private Runnable mCoreFrequencyRunnable;
    private int mCpuNumber;
    private String mSupportedAbis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        //activates recyclerView
        initializeRecyclerView(view);

        recyclerListData();

        getActivity().setTitle(R.string.drawer_system);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        mCoreFrequencyRunnable = () -> {
            for (int i = 0; i < mCpuNumber; i++) {
                int coreFrequency = readIntegerFile("/sys/devices/system/cpu/cpu" + i +
                        "/cpufreq/scaling_cur_freq") / 1000;
                recyclerViewLine.set(i + 2, new RecyclerItemData("Core " + i, String.valueOf(coreFrequency)
                        .concat(" MHz")));
                adapter.notifyDataSetChanged();
            }
            mHandler.postDelayed(mCoreFrequencyRunnable, 5);
        };
        mHandler.postDelayed(mCoreFrequencyRunnable, 100);
    }


    //gets integer value from file
    private static int readIntegerFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePath)), 1000);
            String line = reader.readLine();
            reader.close();

            return Integer.parseInt(line);
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public void onPause() {
        mHandler.removeCallbacks(mCoreFrequencyRunnable);
        super.onPause();
    }


    @Override
    protected void recyclerListData() {
        Runtime runtime = Runtime.getRuntime();
        mCpuNumber = runtime.availableProcessors();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.system_cores), String.valueOf(mCpuNumber)));


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSupportedAbis = new StringBuilder(Build.CPU_ABI).append(" ").append(Build.CPU_ABI2).toString();
        } else {
            for (String s : Build.SUPPORTED_ABIS) {
                mSupportedAbis += s + " ";
            }
        }
        mSupportedAbis = mSupportedAbis.replaceAll("null","");
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.system_instruction_set), mSupportedAbis));


        List<Integer> maxCpuFreqList = new ArrayList<>(mCpuNumber);

        for (int i = 0; i < mCpuNumber; i++) {
            //gets each core frequency
            int coreFrequency = readIntegerFile("/sys/devices/system/cpu/cpu" + i +
                    "/cpufreq/scaling_cur_freq") / 1000;
            recyclerViewLine.add(2 + i, new RecyclerItemData("Core " + i, String.valueOf(coreFrequency)
                    .concat(" MHz")));


            int maxCpuFreq = readIntegerFile("/sys/devices/system/cpu/cpu"+i+"/cpufreq/cpuinfo_max_freq")/1000;
            maxCpuFreqList.add(maxCpuFreq);
        }

        //Clock Speed
        String clockSpeedmin = Collections.min(maxCpuFreqList).toString();
        String clockSpeedmax = Collections.max(maxCpuFreqList).toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.system_clock_speed), new StringBuilder(clockSpeedmin)
                .append(" MHz - ").append(clockSpeedmax).append(" MHz").toString()));


        String osArchitecture = getString(R.string.system_os_architect);
        recyclerViewLine.add(new RecyclerItemData(osArchitecture, System.getProperty("os.arch")));


        String kernel = new StringBuilder(System.getProperty("os.name", "")).append(" ")
                .append(System.getProperty("os.version", "")).toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.system_kernel), kernel));


        String vmLocation = getString(R.string.system_vm_location);
        recyclerViewLine.add(new RecyclerItemData(vmLocation, System.getProperty("java.home",
                getString(R.string.unknown))));


        String jniLibraries = getString(R.string.system_jni_libraries);
        recyclerViewLine.add(new RecyclerItemData(jniLibraries, System.getProperty("java.library.path",
                getString(R.string.unknown))));


        String virtualMachine = new StringBuilder(System.getProperty("java.vm.name", ""))
                .append(" Vendor: ").append(System.getProperty("java.vm.vendor", ""))
                .append(" Version: ").append(System.getProperty("java.vm.version", "")).toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.system_virtual_machine),
                virtualMachine));


        String vmLibraries = new StringBuilder(System.getProperty("java.specification.name", ""))
                .append(" Vendor: ").append(System.getProperty("java.specification.vendor", ""))
                .append(" Version: ").append(System.getProperty("java.specification.version", ""))
                .toString();
        recyclerViewLine.add(new RecyclerItemData(getString(R.string.system_vm_libraries), vmLibraries));


        String bootloader = getString(R.string.system_bootloader);
        recyclerViewLine.add(new RecyclerItemData(bootloader, Build.BOOTLOADER));


        String host = getString(R.string.system_host);
        recyclerViewLine.add(new RecyclerItemData(host, Build.HOST));
    }
}