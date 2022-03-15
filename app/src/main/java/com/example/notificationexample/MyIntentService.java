package com.example.notificationexample;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private Timer mTimer;
    private MusicService.MyTimerTask mMyTimerTask;
    public boolean timer=false;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.notificationexample.action.FOO";
    private static final String ACTION_BAZ = "com.example.notificationexample.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.notificationexample.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.notificationexample.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override
    public void onCreate() {
        super.onCreate();


    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override
    protected void onHandleIntent(Intent intent) {
        /*if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }*/
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
                TimeUnit.SECONDS.sleep(seconds_number + minutes_number * 60 + hours_number * 3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        if (hours_number!=-1&&minutes_number!=-1&&seconds_number!=-1) {
            //создание интента обратной связи
            Intent intent1 = new Intent(this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            //создатьм менеджер уведомлений
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //создать канал уведомлений
            NotificationChannel channel = new NotificationChannel("MusicChannel", "Music", NotificationManager.IMPORTANCE_DEFAULT);
            //активировать канал
            manager.createNotificationChannel(channel);
            //создать настойки уведомления
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "MusicChannel")
                    .setChannelId("MusicChannel")
                    .setContentTitle("Музыка")
                    .setContentText("новый год")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setColor(Color.BLUE)
                    .setContentIntent(pendingIntent);
            //создать уведомление
            Notification notification = nBuilder.build();
            //показать уведомление

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
        }


    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}