package com.controller.massena.controller;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.controller.massena.messages.MessageBuilder;
import com.controller.massena.messages.Msg;
import com.controller.massena.tasks.ExtendedAsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectActivity extends AppCompatActivity {
    private  BluetoothDevice device;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        device = (BluetoothDevice)getIntent().getExtras().getParcelable(MainActivity.EXTRA_MESSAGE);

        if(device.getBondState()==device.BOND_BONDED){
            Toast.makeText(getApplicationContext(), device.getName(),Toast.LENGTH_LONG).show();
        }



        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Msg message =(Msg) msg.obj;
                if(message.getType()==Msg.TYPE.BULETOOTH_STATUS) {
                    Toast.makeText(getApplicationContext(), message.getData().toString(), Toast.LENGTH_LONG).show();
                }
            }
        };


        ExtendedAsyncTask bluetooth=new ExtendedAsyncTask(handler){
            BluetoothSocket tmp = null;

            protected Object doInBackground(Object[] params) {
                try {
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                    tmp.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message =new MessageBuilder(this.getHandler()).setBluetToothStatusType().setData("CONNECTED").setSource(this.toString()).builMessage();
                handler.sendMessage(message);
                while(isRunning()){
                    doIt();
                }
                return null;
            }

            @Override
            public void doIt() {
                InputStream tmpIn = null;
                OutputStream tmpOut = null;
            }
        };
        bluetooth.exec();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, device.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
