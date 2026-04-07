package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, lightSensor, proximitySensor;
    private TextView tvAccelerometer, tvLight, tvProximity;
    private TextView tvAccelSubtitle, tvLightSubtitle, tvProximitySubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        tvAccelerometer = findViewById(R.id.tvAccelerometer);
        tvLight         = findViewById(R.id.tvLight);
        tvProximity     = findViewById(R.id.tvProximity);
        
        tvAccelSubtitle = findViewById(R.id.tvAccelSubtitle);
        tvLightSubtitle = findViewById(R.id.tvLightSubtitle);
        tvProximitySubtitle = findViewById(R.id.tvProximitySubtitle);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            accelerometer  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            lightSensor    = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }

        // Check for sensor availability and update UI
        updateSensorAvailability();
    }

    private void updateSensorAvailability() {
        if (accelerometer == null) {
            tvAccelerometer.setText(R.string.status_unavailable);
            tvAccelerometer.setAlpha(0.5f);
        }
        if (lightSensor == null) {
            tvLight.setText(R.string.status_unavailable);
            tvLight.setAlpha(0.5f);
        }
        if (proximitySensor == null) {
            tvProximity.setText(R.string.status_unavailable);
            tvProximity.setAlpha(0.5f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
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
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                tvAccelerometer.setText(String.format(Locale.getDefault(), "X: %.2f  Y: %.2f  Z: %.2f", x, y, z));
                break;
                
            case Sensor.TYPE_LIGHT:
                float light = event.values[0];
                tvLight.setText(String.format(Locale.getDefault(), "%.1f lx", light));
                break;
                
            case Sensor.TYPE_PROXIMITY:
                float proximity = event.values[0];
                tvProximity.setText(String.format(Locale.getDefault(), "%.1f cm", proximity));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can be used to show sensor health/accuracy if needed
    }
}