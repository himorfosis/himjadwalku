package com.himorfosis.jadwalku;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

import java.io.File;

public class TampilDialogSystemNotifikasi extends BroadcastReceiver {

    AlertDialog alertDialog;
    Context context;
    String judul, isi, gedung, ruang, orang;

    String JUDUL = "JUDUL";
    String ISI = "ISI";
    String GEDUNG = "GEDUNG";
    String RUANG = "RUANG";
    String ORANG = "ORANG";

    @Override
    public void onReceive(Context context, Intent intent) {

        judul = intent.getStringExtra(JUDUL);
        isi = intent.getStringExtra(ISI);
        gedung = intent.getStringExtra(GEDUNG);
        ruang = intent.getStringExtra(RUANG);
        orang = intent.getStringExtra(ORANG);

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        String RESOURCE_PATH = ContentResolver.SCHEME_ANDROID_RESOURCE + "://";

        String path;

        if (false) {

            path = RESOURCE_PATH + context.getPackageName() + "/raw/nadanotifikasi";

        } else {

            int resID = context.getResources().getIdentifier("nadanotifikasi", "raw", context.getPackageName());
            path = RESOURCE_PATH + context.getPackageName() + File.separator + resID;
        }

        Uri soundUri = Uri.parse(path);

        // Vibrate
//        if (Build.VERSION.SDK_INT >= 26) {
//
//            v.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
//
//        } else {
//
//            //deprecated in API 26
//            v.vibrate(10);
//
//        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logoaplikasi)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo))
                .setContentTitle("Kegiatan : "+isi)
                .setContentText("Lokasi : "+gedung+", "+ruang+", 10 menit lagi")
                .setSound(soundUri);

        Intent notifyIntent = new Intent(context, Utama.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, builder.build());

    }

}
