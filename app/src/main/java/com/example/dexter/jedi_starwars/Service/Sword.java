package com.example.dexter.jedi_starwars.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.dexter.jedi_starwars.Helper.MySharedPreference;
import com.example.dexter.jedi_starwars.R;

import java.util.Random;

/**
 * Created by dexter on 12/24/2015.
 */
public class Sword extends Service implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private PowerManager.WakeLock mWakeLock;
    public MediaPlayer mp;
    private MySharedPreference pref;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private int SHAKE_THRESHOLD;
    private String sound;
    @Override
    public void onCreate() {
        super.onCreate();
        pref = new MySharedPreference(getApplicationContext());
        SHAKE_THRESHOLD = pref.getSensitivity();
        sound=pref.getSound();

        Log.i("YAHOO", SHAKE_THRESHOLD + " : " + sound);

        int res = R.raw.sword;
        Boolean random =Boolean.FALSE;
        int[] sounds = {R.raw.sword,R.raw.laser,R.raw.arrow,R.raw.slap, R.raw.steelrod};
        switch(sound){
            case "sword":
                res = sounds[0];
                break;
            case "laser":
                res = sounds[1];
                break;
            case "arrow":
                res = sounds[2];
                break;
            case "slap":
                res = sounds[3];
                break;
            case "steelrod":
                res = sounds[4];
                break;
            case "random":
                random = Boolean.TRUE;
        }


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if(random){
            int min = 0;
            int max = sounds.length-1;
            Random r = new Random();
            int i1 = r.nextInt(max - min + 1) + min;
            mp = MediaPlayer.create(getApplicationContext(), sounds[i1]);
        }else{
            mp = MediaPlayer.create(getApplicationContext(), res);
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("FUCK","hndn");
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    mp.start();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
