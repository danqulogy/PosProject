package com.htlgrieskirchen.posproject.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.htlgrieskirchen.posproject.MainActivity;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.handlers.ReservationHandler;
import com.htlgrieskirchen.posproject.interfaces.CallbackReservation;
import com.htlgrieskirchen.posproject.tasks.ReservationTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class NotificationService extends IntentService {

    NotificationManagerCompat notificationManager;


    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.notificationManager = NotificationManagerCompat.from(getApplicationContext());
        createNotificationChannel();
        while (true) {
            ReservationHandler.getReservationList().forEach(reservation -> {
               if (Duration.between(reservation.getReservationStart(), LocalDateTime.now()).abs().toMinutes() <= 60 && Duration.between(reservation.getReservationStart(), LocalDateTime.now()).abs().toMinutes() >= 30) {
                    createNotification(reservation.getId(), Duration.between(reservation.getReservationStart(), LocalDateTime.now()).abs().toMinutes());
                }
            });
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createNotification(String id, long minutes) {
       NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1337")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(String.format("Your Reservation with ID %s is in %s minutes", id, minutes))
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationCancelChannel";
            String description = "For Notifications when a reservation is cancelled";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    "1337", name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
