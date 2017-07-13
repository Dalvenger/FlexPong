package com.example.banana.testdevice2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Banan on 10/05/2017.
 */

public class TestDevice extends AppCompatActivity implements SuperpoweredUSBAudioHandler{
    private Handler handler;
    private TextView textConsoleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_device);
        textConsoleView = (TextView) findViewById(R.id.consoleTextView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        textConsoleView.setText("\n\n\nNo Midi Device Connected");

        //SuperpoweredUSBAudio usbAudio = new SuperpoweredUSBAudio(getApplicationContext(), this);
        //usbAudio.check();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int[] midi = getLatestMidiMessage();
                switch (midi[0]) {
                    case 8: textConsoleView.setText(String.format(Locale.ENGLISH, "\n\n\nDevice Connected!\n\n Debug Data: Note Off, CH %d, %d, %d", midi[1] + 1, midi[2], midi[3])); break;
                    case 9: textConsoleView.setText(String.format(Locale.ENGLISH, "\n\n\nDevice Connected!\n\n Debug Data: Note On, CH %d, %d, %d", midi[1] + 1, midi[2], midi[3])); break;
                    case 11: textConsoleView.setText(String.format(Locale.ENGLISH, "\n\n\nDevice Connected!\n\n Debug Data: Control Change, CH %d, %d, %d", midi[1] + 1, midi[2], midi[3])); break;
                }
                handler.postDelayed(this, 40);
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 40);



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
