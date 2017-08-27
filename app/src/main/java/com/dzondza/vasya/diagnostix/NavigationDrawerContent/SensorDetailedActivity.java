package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.dzondza.vasya.diagnostix.R;
import java.util.List;


/**
 * contains information about each sensor
 */

public class SensorDetailedActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private int mSensorType;
    private TextView mSensorTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detailed);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        mSensorType = getIntent().getIntExtra(SensorsFragment.SENSOR_TYPE_EXTRA_INTENT, 999);

        mSensorTextView = (TextView) findViewById(R.id.text_sensor_detailed);


        new SensorsFragment().registerAllSensors(this, sensors, mSensorManager);
    }


    //sets sensor's value to mSensorTextView
    private void showSensorsValues(String valueType, Object... eventValues) {
        if (eventValues.length == 3) {
            mSensorTextView.setText(new StringBuilder("x = ").append(eventValues[0]).append(valueType)
                    .append("\ny = ").append(eventValues[1]).append(valueType)
                    .append("\nz = ").append(eventValues[2]).append(valueType).toString());
        } else if (eventValues.length == 1) {
            mSensorTextView.setText(new StringBuilder().append(eventValues[0]).append(valueType)
                    .toString());
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sEvent) {
       Sensor sensor = sEvent.sensor;

        //sets values from sensors to textviews
        if (sensor.getType() == mSensorType) {
            switch (sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                case Sensor.TYPE_LINEAR_ACCELERATION:
                case Sensor.TYPE_GRAVITY:
                    showSensorsValues(" m/s\u00B2", sEvent.values[0], sEvent.values[1], sEvent.values[2]);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                    showSensorsValues(" rad/s", sEvent.values[0], sEvent.values[1], sEvent.values[2]);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                    showSensorsValues(" \u00b5T", sEvent.values[0], sEvent.values[1], sEvent.values[2]);
                    break;
                case Sensor.TYPE_ORIENTATION:
                case Sensor.TYPE_ROTATION_VECTOR:
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                    showSensorsValues("", sEvent.values[0], sEvent.values[1], sEvent.values[2]);
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    showSensorsValues(" Celsius Degree", sEvent.values[0]);
                    break;
                case Sensor.TYPE_PRESSURE:
                    showSensorsValues(" hPa", sEvent.values[0]);
                    break;
                case Sensor.TYPE_LIGHT:
                    showSensorsValues(" lux", sEvent.values[0]);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    showSensorsValues(" cm", sEvent.values[0]);
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    showSensorsValues(" %", sEvent.values[0]);
                    break;
                case Sensor.TYPE_SIGNIFICANT_MOTION:
                case Sensor.TYPE_STEP_COUNTER:
                    showSensorsValues("", sEvent.values[0]);
                    break;
                default:
                    mSensorTextView.setText(new StringBuilder("Power ").append(sensor.getPower())
                        .append(" mA\nResolution ").append(sensor.getResolution())
                            .append(" lx\nMax Range ").append(sensor.getMaximumRange())
                            .append(" lx\nVersion ").append(sensor.getVersion()).toString());
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}