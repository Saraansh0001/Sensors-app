package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Step 1: Declare variables
    SensorManager sensorManager;
    Sensor accelerometer, lightSensor, proximitySensor;

    TextView tvAccelerometer, tvLight, tvProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Step 2: Connect TextViews to XML
        tvAccelerometer = findViewById(R.id.tvAccelerometer);
        tvLight         = findViewById(R.id.tvLight);
        tvProximity     = findViewById(R.id.tvProximity);

        // Step 3: Get the SensorManager system service
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Step 4: Get each sensor
        accelerometer  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor    = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    // Step 5: Register sensors when app is visible
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,  SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor,    SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Step 6: Unregister sensors when app is paused (saves battery)
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // Step 7: This method is called automatically when sensor data changes
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            tvAccelerometer.setText("X: " + x + "  Y: " + y + "  Z: " + z);
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = event.values[0];
            tvLight.setText("Light: " + light + " lx");
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float proximity = event.values[0];
            tvProximity.setText("Proximity: " + proximity + " cm");
        }
    }

    // Step 8: Required method - we don't need it but must include it
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}