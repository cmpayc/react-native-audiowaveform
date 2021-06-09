package com.otomogroove.OGReactNativeWaveform;

import androidx.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.ringdroid.WaveformView;
import com.ringdroid.soundfile.SoundFile;

import java.io.File;

/**
 * Created by juanjimenez on 13/01/2017.
 */

public class OGWaveManager extends SimpleViewManager<OGWaveView> implements LifecycleEventListener {

    public static final String REACT_CLASS = "OGWave";
    private ReactContext mcontext;

    private Boolean isNetwork;

    @Override
    public String getName() {

        return REACT_CLASS;
    }

    @Override
    public OGWaveView createViewInstance(ThemedReactContext context) {
        context.addLifecycleEventListener(this);
        mcontext = context;

        OGWaveView mWaveView = new OGWaveView(context);

        return mWaveView;
    }

    @Override
    public void onDropViewInstance(OGWaveView view) {
        super.onDropViewInstance(view);

        if (this.isNetwork) {
            SoundFile soundFile = view.getSoundFile();

            if (soundFile != null) {
                File file = new File(soundFile.getInputFile().getPath());
                boolean deleted = file.delete();
                Log.w("XSXGOT", "File deleted: " + deleted);
            }
        }
    }

    @Override
    public void setTestId(OGWaveView view, String testId) {
        super.setTestId(view, testId);
        Log.e("XSXGOT","TTTTTTTTTT::: "+ testId);
    }

    @ReactMethod
    public void testCallback(Callback cb) {

        Log.e("XSXGOT","TESXT CA:LBACK CALLED!!! ");
        String sampleText = "Java is fun";
        int textLength = sampleText.length();
        try{
            cb.invoke(textLength);
        }catch (Exception e){
            cb.invoke("err");
        }
    }

    @ReactProp(name = "src")
    public void setSrc(OGWaveView view, @Nullable ReadableMap src) {
        this.isNetwork = src.getBoolean("isNetwork");
        view.setIsNetwork(this.isNetwork);
        view.setURI(src.getString("uri"));
    }

    @ReactProp(name="componentID")
    public void setComponentID(OGWaveView view, String componentID) {
        Log.e("XSXGOT","componentID SETTED:::!!!!" +componentID);
        view.setComponentID(componentID);

    }

    @ReactProp(name = "waveFormStyle")
    public void setWaveFormStyle(OGWaveView view, @Nullable ReadableMap waveFormStyle) {
        view.setmWaveColor(waveFormStyle.getInt("ogWaveColor"));
        view.setScrubColor(waveFormStyle.getInt("ogScrubColor"));
        view.setBackgroundColor(waveFormStyle.getInt("ogBackgroundColor"));
    }

    @ReactProp(name = "pos")
    public void setPos(OGWaveView view, @Nullable float pos) {
        Log.e("POSPOS","position:::: "+pos);
        view.onPos(pos);
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
