package com.gipflstuermer.gipfl.tools;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.Observable;

/**
 * Created by Vincent on 04.12.2015.
 */
public class Barometer extends Observable implements SensorEventListener  {

    private SensorManager mSensorManager;
    private Sensor mSensorTemperature;
    private Sensor mSensorPressure;
    private Sensor mSensorHumidity;
    private float temperature;
    private float pressure;
    private float humidity;


    public Barometer(){
        /*
        this.temperature = null;
        this.pressure = null;
        this. humidity = null;
        */
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mSensorTemperature) {
            this.temperature = event.values[0];
        }

        if(event.sensor == mSensorPressure) {
            this.pressure = event.values[0];
            setChanged();
            notifyObservers();
        }

        if(event.sensor == mSensorHumidity) {
            this.humidity = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getTemperature(){
        return this.temperature;
    }

    public float getPressure(){
        return this.pressure;
    }

    public float getHumidity(){
        return this.humidity;
    }
}
