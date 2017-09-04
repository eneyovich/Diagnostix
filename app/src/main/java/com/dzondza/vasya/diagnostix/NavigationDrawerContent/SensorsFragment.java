package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;
import java.util.List;


/**
 * contains all mSensors' names and vendors
 */

public class SensorsFragment extends BaseDetailedFragment implements SensorEventListener{
    private SensorManager mSensorManager;
    private List<Sensor> mSensors;

    public static final String SENSOR_TYPE_EXTRA_INTENT = "sensorType";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        //activates recyclerView
        initializeRecyclerView(view);

        //initializes mSensors' list
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        registerAllSensors(this, mSensors, mSensorManager);
        for (Sensor sensor : mSensors) {
            recyclerViewLine.add(new RecyclerItemsData(sensor.getName(), sensor.getVendor().concat("  --->")));
        }


        getActivity().setTitle(R.string.drawer_sensors);

        return view;
    }


    void registerAllSensors(SensorEventListener sensorListener,
                            List<Sensor> sensorList, SensorManager sensManager) {
        for (Sensor sensor: sensorList) {
            if (sensor != null) {
                sensManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        for (int i = 0; i < mSensors.size(); i++) {
            if (position == i) {
                Intent intent = new Intent(getActivity(), SensorDetailedActivity.class);
                intent.putExtra(SENSOR_TYPE_EXTRA_INTENT, mSensors.get(i).getType());
                startActivity(intent);
            }
        }
        super.onItemClick(adapterView, view, position, l);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {}

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSensorManager.unregisterListener(this);
    }
}