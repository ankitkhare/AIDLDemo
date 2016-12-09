package com.mk.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ANKIT on 09,December,2016
 */

public class MyService extends Service {

    /**
     * Service stub
     */
    private final IBinder mBinder = new MyStub(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public String calculateSum(int x, int y) {
        return String.valueOf(x + y);
    }

    public String calculateMultiplication(int x, int y) {
        return String.valueOf(x * y);
    }

    public String calculateDivision(int x, int y) {
        if (y != 0)
            return String.valueOf(x / y);
        else
            return "Invalid Operation";
    }

    public String calculateSubstraction(int x, int y) {
        return String.valueOf(x - y);
    }
}
