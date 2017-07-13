package com.example.banana.testdevice2;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Banan on 19/04/2017.
 */

public class GameState extends AppCompatActivity implements SuperpoweredUSBAudioHandler{
    //screen width and height
    final int _screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final int _screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    final int _centerScreenWidth = _screenWidth /2;
    final int _centerScreenHeight = _screenHeight /3;

    //ball
    final int _ballSize = 30;
    int _ballX = _centerScreenHeight; 	int _ballY = _centerScreenWidth;
    int _ballVelocityX = 10; 	int _ballVelocityY = 10;

    //bat
    final int _batLength = 250;	final int _batHeight = 75;
    int _bottomBatX = (_screenWidth/2) - (_batLength / 2);
    int  tempBottomBatY = Resources.getSystem().getDisplayMetrics().heightPixels;
    final int _bottomBatY = tempBottomBatY - 30;
    final int _batSpeed = 8;
    int _pauseZone = 20;

    private int currentDeviceTarget;

    public int getCurrentDeviceTarget()
    {
        return currentDeviceTarget;
    }

    public void setCurrentDeviceTarget(int position)
    {
        currentDeviceTarget = position;
    }

    private int playerScore;

    public int getPlayerScore(){
        return playerScore;
    }
    public void addPlayerScore(){
        playerScore += 1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuperpoweredUSBAudio usbAudio = new SuperpoweredUSBAudio(getApplicationContext(), this);
        usbAudio.check();

    }
    public GameState()
    {

    }

    public void update() {

        _ballX += _ballVelocityX;
        _ballY += _ballVelocityY;

        if(_ballY > _screenHeight - 20)
        {_ballX = _centerScreenHeight; 	_ballY = _centerScreenWidth;}

        if( _ballY < 0){
            _ballVelocityY *= -1;
        }

        //Collisions with the bats
        if(_ballX > _screenWidth || _ballX < 30)
            _ballVelocityX *= -1;

        //Collisions with the bat
        if(_ballX > _bottomBatX && _ballX < _bottomBatX+_batLength
                && _ballY > _bottomBatY) {
            _ballVelocityY *= -1;
            addPlayerScore();
        }


        int[] midi = getLatestMidiMessage();

        if (midi[2] == 30 || midi[2] == 10)
        {
            setCurrentDeviceTarget(midi[3]);
        }

        int tempWidth = _screenWidth / 127;

        if (getCurrentDeviceTarget() >= _bottomBatX%tempWidth + _pauseZone) {
            _bottomBatX += _batSpeed;
        }
        else if (getCurrentDeviceTarget() <= _bottomBatX - _pauseZone) {
            _bottomBatX -= _batSpeed;
        }

    }

    public boolean keyPressed(int keyCode, KeyEvent msg)
    {
        if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) //left
        {
           _bottomBatX -= _batSpeed;
        }

        if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) //right
        {
            _bottomBatX += _batSpeed;
        }

        return true;
    }

    public void draw(Canvas canvas, Paint paint) {

        //background colour
        canvas.drawRGB(0, 0, 0);

        //paddle/balle colour
        paint.setARGB(255, 255, 255, 255);

        //ball
        canvas.drawCircle(_ballX,_ballY, _ballSize, paint);

        //bat
        canvas.drawRect(new Rect(_bottomBatX, _bottomBatY, _bottomBatX + _batLength,
                _bottomBatY + _batHeight), paint);

        //score text
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("Score: " + getPlayerScore(), _screenWidth - 200, 100, paint);

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