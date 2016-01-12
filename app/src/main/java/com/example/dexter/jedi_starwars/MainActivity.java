package com.example.dexter.jedi_starwars;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dexter.jedi_starwars.Helper.MySharedPreference;
import com.example.dexter.jedi_starwars.Service.Sword;

public class MainActivity extends Activity {

     private MySharedPreference pref;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = new MySharedPreference(getApplicationContext());
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.MainWindow);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                ViewDialog alert = new ViewDialog();
                alert.showDialog(MainActivity.this, "Just Do it");

            }
        });
        intent = new Intent(MainActivity.this,Sword.class);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class ViewDialog {

        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);

            final NumberPicker numberPicker = (NumberPicker)dialog.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(10);
            final RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.radioGroup);
            final CheckBox checkbox= (CheckBox)dialog.findViewById(R.id.checkbox);
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int senseitivityValue = numberPicker.getValue();

                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    View radioButton = radioGroup.findViewById(selectedId);
                    int weapon = radioGroup.indexOfChild(radioButton);

                    if(checkbox.isChecked()){
                        stopService(intent);
                        Log.i("YAHOO", "Checkbox Checked");
                    }else{
                        Log.i("YAHOO", "Checkbox Not Checked");
                        pref.setSensitivity((11 - senseitivityValue) * 300);
                        Log.i("YAHOO", "WEapon is : " + weapon);
                        switch(weapon){
                            case 0:
                                pref.setSound("sword");
                                break;
                            case 1:
                                pref.setSound("laser");
                                break;
                            case 2:
                                pref.setSound("arrow");
                                break;
                            case 3:
                                pref.setSound("slap");
                                break;
                            case 4:
                                pref.setSound("steelrod");
                                break;
                            case 5:
                                pref.setSound("random");
                                break;
                        }
                        Log.i("YAHOO", "Stopping and Starting");
                        //stopService(serviceIntent);
                        //startService(serviceIntent);
                        stopService(intent);
                        Log.i("YAHOO", pref.getSound());

                        startService(intent);

                    }


                    Log.i("YAHOO", selectedId + " : " + senseitivityValue);
                    dialog.dismiss();
                }
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            dialog.show();
            dialog.getWindow().setAttributes(lp);


        }
    }
}