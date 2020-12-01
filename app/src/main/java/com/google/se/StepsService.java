package com.google.se;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class StepsService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private StepsDBHelper mStepsDBHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager)
                this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mStepDetectorSensor =
                    mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            mStepsDBHelper = new StepsDBHelper(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int
            startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mStepsDBHelper.createStepsEntry();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
