package com.mk.aidldemo;

import android.os.RemoteException;

import java.lang.ref.WeakReference;

/**
 * Created by ANKIT on 09,December,2016
 */

public class MyStub extends IMyAidlInterface.Stub {

    private final WeakReference<MyService> mService;

    public MyStub(final MyService service) {
        mService = new WeakReference<>(service);
    }


    @Override
    public String sum(int x, int y) throws RemoteException {
        return mService.get().calculateSum(x, y);
    }

    @Override
    public String mutliplt(int x, int y) throws RemoteException {
        return mService.get().calculateMultiplication(x, y);
    }

    @Override
    public String divide(int x, int y) throws RemoteException {
        return mService.get().calculateDivision(x, y);
    }

    @Override
    public String substract(int x, int y) throws RemoteException {
        return mService.get().calculateSubstraction(x, y);
    }
}
