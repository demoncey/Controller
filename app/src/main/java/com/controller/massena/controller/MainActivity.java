package com.controller.massena.controller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {

        private BluetoothAdapter ba;
        private Set<BluetoothDevice> pairedDevices;
        Button turn_on,turn_off,devices,visible;
        ListView dev_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create bluetooth adapter
        ba = BluetoothAdapter.getDefaultAdapter();


        turn_on = (Button) findViewById(R.id.turn_on);
        turn_off = (Button) findViewById(R.id.turn_off);
        devices = (Button) findViewById(R.id.devices);
        dev_list = (ListView)findViewById(R.id.dev_list);
        visible = (Button)findViewById(R.id.visible);


        //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //registerReceiver(MydeviceReceiver, filter);

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

        devices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list(v);
            }
        });


        visible.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                visible(v);
            }
        });


        dev_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        } );
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

    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    public void list(View v) {
        pairedDevices = ba.getBondedDevices();
        ArrayList list = new ArrayList();
        for(BluetoothDevice bt : pairedDevices) list.add(bt.getName()+" "+bt.getAddress());
        Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

        dev_list.setAdapter(adapter);



    }


}
