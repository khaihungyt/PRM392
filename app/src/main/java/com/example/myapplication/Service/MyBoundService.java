package com.example.myapplication.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyBoundService extends Service {
    private int counter = 0;
    private final Binder binder = new MyBinder();

    public class MyBinder extends Binder {
        public MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    public MyBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        counter++;
        int add = intent.getIntExtra("add", 5);
        counter += add;
        return binder;
    }

    public int getCounter() {
        return counter;
    }

}