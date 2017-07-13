package com.example.banana.testdevice2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.Test;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SuperpoweredUSBAudioHandler{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        Button midiTestButton = (Button) findViewById(R.id.midiTestButton);


        newGameButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, GameRound.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);}});

        midiTestButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, TestDevice.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);}});


        SuperpoweredUSBAudio usbAudio = new SuperpoweredUSBAudio(getApplicationContext(), this);
        usbAudio.check();

        int midi[] = getLatestMidiMessage();
    }

    @Override
    public void onUSBAudioDeviceAttached(int deviceID) {

    }

    @Override
    public void onUSBMIDIDeviceAttached(int deviceID) {

    }

    @Override
    public void onUSBDeviceDetached(int deviceID) {

    }
    private native int[]getLatestMidiMessage();

    static {
        System.loadLibrary("SuperpoweredExample");
    }
}
