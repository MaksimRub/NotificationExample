package com.example.notificationexample;

import static android.content.Intent.getIntent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    public boolean timer=false;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=MediaPlayer.create(this,R.raw.newyear);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //создание интента обратной связи
        Intent intent1=new Intent(this,MainActivity.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        //создатьм менеджер уведомлений
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //создать канал уведомлений
        NotificationChannel channel=new NotificationChannel("MusicChannel","Music",NotificationManager.IMPORTANCE_DEFAULT);
        //активировать канал
        manager.createNotificationChannel(channel);
        //создать настойки уведомления
        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(this,"MusicChannel")
                .setChannelId("MusicChannel")
                .setContentTitle("Музыка")
                .setContentText("новый год")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent);
        //создать уведомление
        Notification notification=nBuilder.build();
        //показать уведомление
        String hours_text=intent.getStringExtra("hours");
        String minutes_text=intent.getStringExtra("minutes");
        String seconds_text=intent.getStringExtra("seconds");
        int hours_number=-1;
        int minutes_number=-1;
        int seconds_number=-1;
        try {
            hours_number=Integer.parseInt(hours_text);
            minutes_number=Integer.parseInt(minutes_text);
            seconds_number=Integer.parseInt(seconds_text);
        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "введите числа", Toast.LENGTH_SHORT).show();
        }
        try {
            TimeUnit.SECONDS.sleep(seconds_number+minutes_number*60+hours_number*3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*if (mTimer != null) {
            mTimer.cancel();
        }
        if(hours_number!=-1&&minutes_number!=-1&&seconds_number!=-1) {
            mTimer = new Timer();
            mMyTimerTask = new MyTimerTask();
            int time=3600000 * hours_number +minutes_number * 60000+seconds_number*1000;
            mTimer.schedule(mMyTimerTask, time);
        }
        while(!timer) {

        }*/
        manager.notify(475648, notification);

        //удаление уведомления
        //manager.cancel(475648);
        return Service.START_STICKY;
    }



    class MyTimerTask extends TimerTask {


        @Override
        public void run() {
            timer=true;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}

