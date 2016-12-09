package com.mk.aidldemo;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.mk.aidldemo.ServiceUtils.mService;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    // The service token
    private ServiceUtils.ServiceToken mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind service
        mToken = ServiceUtils.bindToService(this, this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = IMyAidlInterface.Stub.asInterface(service);

        //Get result here from AIDL
        Log.e("Sum result", ServiceUtils.sum(100, 20));
        Log.e("Multiply result", ServiceUtils.multiply(100, 20));
        Log.e("Substraction result", ServiceUtils.substraction(100, 20));
        Log.e("Division result", ServiceUtils.divide(100, 20));
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            ServiceUtils.unbindFromService(mToken);
            mToken = null;
        }
    }
}
