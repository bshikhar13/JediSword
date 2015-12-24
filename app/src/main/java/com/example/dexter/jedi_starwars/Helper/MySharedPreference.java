package com.example.dexter.jedi_starwars.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by dexter on 12/24/2015.
 */
public class MySharedPreference {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "JediSP";

    // All Shared Preferences Keys
    private static final String SOUND = "sound";
    private static final String SENSITIVITY = "sensitivity";

    public MySharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getSound(){
        return pref.getString(SOUND,"laser");
    }

    public int getSensitivity(){
        return pref.getInt(SENSITIVITY,1500);
    }

    public void setSound(String sound){
        Log.i("YAHOO1", sound);
        editor.putString(SOUND, sound);
        editor.commit();

    }

    public void setSensitivity(int sensitivity){
        editor.putInt(SENSITIVITY, sensitivity);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }


}
