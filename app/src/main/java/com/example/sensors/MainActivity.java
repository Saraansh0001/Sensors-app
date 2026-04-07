package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, lightSensor, proximitySensor;
    private TextView tvAccelerometer, tvLight, tvProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        tvAccelerometer = findViewById(R.id.tvAccelerometer);
        tvLight         = findViewById(R.id.tvLight);
        tvProximity     = findViewById(R.id.tvProximity);

        // Get SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            // Get specific sensors as per question requirement
            accelerometer   = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            lightSensor     = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }

        // Initial check for sensor availability
        checkAvailability();
    }

    private void checkAvailability() {
        if (accelerometer == null) tvAccelerometer.setText(R.string.status_unavailable);
        if (lightSensor == null) tvLight.setText(R.string.status_unavailable);
        if (proximitySensor == null) tvProximity.setText(R.string.status_unavailable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            // Register listeners for required sensors
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            }
            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI);
            }
            if (proximitySensor != null) {
                sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            // Unregister to save battery
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        
        if (type == Sensor.TYPE_ACCELEROMETER) {
            tvAccelerometer.setText(String.format(Locale.getDefault(), 
                "X: %.2f  Y: %.2f  Z: %.2f", event.values[0], event.values[1], event.values[2]));
        } else if (type == Sensor.TYPE_LIGHT) {
            tvLight.setText(String.format(Locale.getDefault(), "%.1f lx", event.values[0]));
        } else if (type == Sensor.TYPE_PROXIMITY) {
            tvProximity.setText(String.format(Locale.getDefault(), "%.1f cm", event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not required for basic display
    }
}