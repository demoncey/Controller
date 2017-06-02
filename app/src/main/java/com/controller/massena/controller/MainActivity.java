package com.controller.massena.controller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

        private BluetoothAdapter ba;
        Button turn_on,turn_off;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create bluetooth adapter
        ba = BluetoothAdapter.getDefaultAdapter();


        turn_on = (Button) findViewById(R.id.turn_on);
        turn_off = (Button) findViewById(R.id.turn_off);

        turn_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                on(v);
            }
        });

        turn_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                off(v);
            }
        });

    }

    public void on(View v){
            if (!ba.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 0);
                Toast.makeText(getApplicationContext(), "Bluetooth turned on",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth already on", Toast.LENGTH_LONG).show();
            }
        }
    public void off(View v){
        ba.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }



}
