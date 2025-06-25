package com.example.myapplication.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

public class MyUnboundService extends Service {
    public MyUnboundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        String msg= intent.getStringExtra("msg");
        sendMessage(msg);
        return START_STICKY;
    }
    private void sendMessage(String msg){
        String CHANNEL_ID="my_Chanel_Id";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_action_favourite)
                        .setContentTitle("My notification")
                        .setContentText("message send from acivity: "+msg)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(false);
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"My Demo Channel123",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1,builder.build());
    }
}