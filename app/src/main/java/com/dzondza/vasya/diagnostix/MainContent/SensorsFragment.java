package com.dzondza.vasya.diagnostix.MainContent;

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
 * contains all sensors' names and vendors
 */

public class SensorsFragment extends BaseDetailedFragment implements SensorEventListener{
    private SensorManager sensorManager;
    private List<Sensor> sensors;

    public static final String SENSOR_TYPE_EXTRA_INTENT = "sensorType";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // init recyclerView List
        initializeRecyclerView(view);


        //initialize sensors' list
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);


        registerAllSensors(this, sensors, sensorManager);
        for (Sensor sensor : sensors) {
            recyclerViewLine.add(new RecyclerItemsData(sensor.getName(), sensor.getVendor().concat("  --->")));
        }

        //toolbar title
        getActivity().setTitle(R.string.drawer_sensors);

        return view;
    }


    protected void registerAllSensors(SensorEventListener sensorListener,
                                      List<Sensor>sensorList, SensorManager sensManager) {
        for (Sensor sensor: sensorList) {
            if (sensor != null) {
                sensManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        for (int i = 0; i < sensors.size(); i++) {
            if (position == i) {
                Intent intent = new Intent(getActivity(), SensorDetailedActivity.class);
                intent.putExtra(SENSOR_TYPE_EXTRA_INTENT, sensors.get(i).getType());
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
        sensorManager.unregisterListener(this);
    }
}