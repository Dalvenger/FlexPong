package com.example.banana.testdevice2;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Banan on 19/04/2017.
 */


public class PongView extends SurfaceView  implements SurfaceHolder.Callback
{

    private GameThread thread;

    public PongView(Context context, AttributeSet attrs) {
        super(context, attrs);


        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);

        thread = new GameThread(holder, context, new Handler());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return thread.getGameState().keyPressed(keyCode, msg);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stop();
    }
}