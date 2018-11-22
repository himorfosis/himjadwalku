package com.himorfosis.jadwalku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class TampilDialogSystemPresensi extends BroadcastReceiver {

    AlertDialog alertDialog;
    Context context;

    String kegiatan, isi, tempat;


    @Override
    public void onReceive(Context context, Intent intent) {

        kegiatan = intent.getStringExtra("kegiatan");
        isi = intent.getStringExtra("isi");
        tempat = intent.getStringExtra("tempat");

        Intent scheduledIntent = new Intent(context, TampilPresensi.class);
        scheduledIntent.putExtra("kegiatan", kegiatan);
        scheduledIntent.putExtra("isi", isi);
        scheduledIntent.putExtra("tempat", tempat);

        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scheduledIntent);

    }

}
