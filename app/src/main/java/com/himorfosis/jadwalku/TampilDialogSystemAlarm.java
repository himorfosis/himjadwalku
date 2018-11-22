package com.himorfosis.jadwalku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class TampilDialogSystemAlarm extends BroadcastReceiver {

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
        // Add as notification

        judul = intent.getStringExtra(JUDUL);
        isi = intent.getStringExtra(ISI);
        gedung = intent.getStringExtra(GEDUNG);
        ruang = intent.getStringExtra(RUANG);
        orang = intent.getStringExtra(ORANG);

        Intent scheduledIntent = new Intent(context, TampilAlarm.class);
        scheduledIntent.putExtra(JUDUL, judul);
        scheduledIntent.putExtra(ISI, isi);
        scheduledIntent.putExtra(GEDUNG, gedung);
        scheduledIntent.putExtra(RUANG, ruang);
        scheduledIntent.putExtra(ORANG, orang);
        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scheduledIntent);

    }

}
