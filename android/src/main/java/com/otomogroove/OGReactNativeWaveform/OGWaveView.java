package com.otomogroove.OGReactNativeWaveform;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReactContext;
import com.ringdroid.WaveformView;
import com.ringdroid.soundfile.SoundFile;

import java.io.IOException;

import static com.facebook.react.common.ReactConstants.TAG;

/**
 * Created by juanjimenez on 13/01/2017.
 */

public class OGWaveView extends FrameLayout {


    private final OGUIWaveView mUIWave;
    private WaveformView mWaveView;
    private SoundFile soundFile;

    private String componentID;

    private ReactContext mContext;

    private boolean isCreated = false;


    public OGWaveView(ReactContext context) {
        super(context);
        mContext = context;

        mWaveView = new WaveformView(mContext, this);
        mUIWave = new OGUIWaveView(mContext);


        // mUIWave.setBackgroundColor(Color.TRANSPARENT);
        mUIWave.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setmWaveColor(int mWaveColor) {
        mWaveView.setmWaveColor(mWaveColor);
    }

    public void setScrubColor(int scrubcolor){
        mUIWave.scrubColor = scrubcolor;
        mUIWave.invalidate();
    }

    public void setBackgroundColor(int backgroudColor){
        mWaveView.setBackgroundColor(backgroudColor);
    }

    public void onPos(float pos) {
        mUIWave.updatePlayHead(pos);
    }

    public void setURI(String uri){
        // Create the MediaPlayer

        Log.d("XSXGOT", "Setting URI to: " + uri);

        if (uri.isEmpty()) {
            Log.d("XSXGOT", "URI is empty");
            return;
        }

        this.mWaveView.setmURI(uri);

        if (!isCreated) {
            isCreated = true;
            addView(this.mWaveView);
            addView(this.mUIWave);
        }

        mUIWave.updatePlayHead(0);
    }

    public void setIsNetwork(Boolean isNetwork){
        this.mWaveView.setIsNetwork(isNetwork);
    }

    public SoundFile getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(SoundFile soundFile) {
        this.soundFile = soundFile;
    }

    public String getComponentID() {
        return componentID;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }

}
