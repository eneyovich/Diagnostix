package com.dzondza.vasya.diagnostix.MainContent;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;

/**
 * contains information about battery
 */

public class BatteryFragment extends BaseDetailedFragment {

    private Intent batteryIntent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);


        // init recyclerView List
        initializeRecyclerView(view);


        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent = getActivity().registerReceiver(null, intentFilter);


        if (Build.VERSION.SDK_INT >= 22) {
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.battery_saver_settings), getString(R.string.open)));
        }


        if (Build.VERSION.SDK_INT >= 23) {
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.battery_optimization_settings),
                    getString(R.string.open)));
        }


        String powerSource = getString(R.string.battery_power_source);
        recyclerViewLine.add(new RecyclerItemsData(powerSource, chargeSource()));


        String levelDescription = getString(R.string.battery_level);
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        String levelSolution = String.valueOf(level).concat(" %");
        recyclerViewLine.add(new RecyclerItemsData(levelDescription, levelSolution));


        String batteryStatus = getString(R.string.battery_status);
        recyclerViewLine.add(new RecyclerItemsData(batteryStatus, powerStatus()));


        String tempDescription = getString(R.string.battery_temperature_battery);
        float temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10 +
                batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) % 10;
        String tempSolution = String.valueOf(temperature).concat(" C");
        recyclerViewLine.add(new RecyclerItemsData(tempDescription, tempSolution));


        String batteryVoltage = getString(R.string.battery_voltage);
        recyclerViewLine.add(new RecyclerItemsData(batteryVoltage, voltageLevel()));


        String batteryHealth = getString(R.string.battery_health);
        recyclerViewLine.add(new RecyclerItemsData(batteryHealth, healthStatus()));


        String technologyDescription = getString(R.string.battery_technology);
        String technologySolution = batteryIntent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
        recyclerViewLine.add(new RecyclerItemsData(technologyDescription, technologySolution));


        if (Build.VERSION.SDK_INT >= 21) {
            String capacityDescription = getString(R.string.battery_capacity);
            recyclerViewLine.add(new RecyclerItemsData(capacityDescription, getBatteryCapacity()));
        }

        //toolbar title
        getActivity().setTitle(R.string.drawer_battery);

        return view;
    }


    private String chargeSource() {
        String batterySource;

        switch (batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)){
            case BatteryManager.BATTERY_PLUGGED_AC:
                batterySource = getString(R.string.battery_ac_source);
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                batterySource = getString(R.string.battery_usb_source);
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                batterySource = getString(R.string.battery_wireless_source);
                break;
            case 0:
                batterySource = getString(R.string.battery_source_battery);
                break;
            default:
                batterySource = getString(R.string.unknown);
        }

        return batterySource;
    }


    private String powerStatus() {
        String batteryStatus;

        switch (batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                batteryStatus = getString(R.string.battery_charging);
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                batteryStatus = getString(R.string.battery_discharging);
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                batteryStatus = getString(R.string.battery_full);
                break;
            default:
                batteryStatus = getString(R.string.unknown);
        }

        return batteryStatus;
    }


    private String voltageLevel() {

        int numberFirst = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000;
        int numberSecond = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) % 1000 / 100;
        int numberThird = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) % 100 / 10;
        int numberFourth = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) % 10;

        return new StringBuilder().append(numberFirst).append(",").append(numberSecond)
                .append(numberThird).append(numberFourth).append(" V").toString();
    }


    private String healthStatus() {
        String health;

        switch (batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                health = getString(R.string.battery_health_cold);
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                health = getString(R.string.battery_health_dead);
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                health = getString(R.string.battery_health_good);
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health = getString(R.string.battery_health_overheat);
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health = getString(R.string.battery_health_over_voltage);
                break;
            default:
                health = getString(R.string.unknown);
        }
        return health;
    }


    //gets battery capacity using Reflection
    private String getBatteryCapacity() {
        double batteryCapacity = 0.0;

        try{
            Object powerProfile = Class.forName("com.android.internal.os.PowerProfile")
                    .getConstructor(Context.class).newInstance(getActivity());

            batteryCapacity = (double) Class.forName("com.android.internal.os.PowerProfile")
                    .getMethod("getBatteryCapacity")
                    .invoke(powerProfile);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(Math.round(batteryCapacity)).concat(" Mah");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        try {
            if (position == 0 && Build.VERSION.SDK_INT >= 22) {
                startActivity(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
            }
            if (position == 1 && Build.VERSION.SDK_INT >= 23) {
                startActivity(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS));
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.option_unavailable), Toast.LENGTH_SHORT)
                    .show();
        }

        super.onItemClick(adapterView, view, position, l);
    }
}