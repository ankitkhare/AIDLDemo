package com.mk.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.WeakHashMap;

/**
 * Created by ANKIT on 09,December,2016
 */

public class ServiceUtils {

    public static IMyAidlInterface mService = null;
    private static final WeakHashMap<Context, ServiceBinder> mConnectionMap;

    static {
        mConnectionMap = new WeakHashMap<>();
    }

    public static final ServiceToken bindToService(final Context context,
                                                   final ServiceConnection callback) {
        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }
        final ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        Intent serviceIntent = new Intent(contextWrapper, MyService.class);
        contextWrapper.startService(serviceIntent);
        final ServiceBinder binder = new ServiceBinder(callback);
        if (contextWrapper.bindService(
                new Intent().setClass(contextWrapper, MyService.class), binder, 0)) {
            mConnectionMap.put(contextWrapper, binder);
            return new ServiceToken(contextWrapper);
        }
        return null;
    }


    public static void unbindFromService(final ServiceToken token) {
        if (token == null) {
            return;
        }
        final ContextWrapper mContextWrapper = token.mWrappedContext;
        final ServiceBinder mBinder = mConnectionMap.remove(mContextWrapper);
        if (mBinder == null) {
            return;
        }
        mContextWrapper.unbindService(mBinder);
        if (mConnectionMap.isEmpty()) {
            mService = null;
        }
    }

    public static final class ServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;


        public ServiceBinder(final ServiceConnection callback) {
            mCallback = callback;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            mService = IMyAidlInterface.Stub.asInterface(service);
            if (mCallback != null) {
                mCallback.onServiceConnected(className, service);
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(className);
            }
            mService = null;
        }
    }

    public static final class ServiceToken {
        public ContextWrapper mWrappedContext;
        
        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }

    public static final String sum(int x, int y) {
        if (mService != null) {
            try {
                return mService.sum(x, y);
            } catch (final RemoteException ignored) {
            }
        }
        return "failed";
    }

    public static final String substraction(int x, int y) {
        if (mService != null) {
            try {
                return mService.substract(x, y);
            } catch (final RemoteException ignored) {
            }
        }
        return "failed";
    }

    public static final String divide(int x, int y) {
        if (mService != null) {
            try {
                return mService.divide(x, y);
            } catch (final RemoteException ignored) {
            }
        }
        return "failed";
    }

    public static final String multiply(int x, int y) {
        if (mService != null) {
            try {
                return mService.mutliplt(x, y);
            } catch (final RemoteException ignored) {
            }
        }
        return "failed";
    }
}
