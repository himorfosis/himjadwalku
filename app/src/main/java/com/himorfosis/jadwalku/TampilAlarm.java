package com.himorfosis.jadwalku;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TampilAlarm extends AppCompatActivity {

    String judul, isi, gedung, ruang, orang;

    Context context;
    AlertDialog alertDialog;
    MediaPlayer m;

    String JUDUL = "JUDUL";
    String ISI = "ISI";
    String GEDUNG = "GEDUNG";
    String RUANG = "RUANG";
    String ORANG = "ORANG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_alarm);

        judul = getIntent().getStringExtra(JUDUL);
        isi = getIntent().getStringExtra(ISI);
        gedung = getIntent().getStringExtra(GEDUNG);
        ruang = getIntent().getStringExtra(RUANG);
        orang = getIntent().getStringExtra(ORANG);

        m = new MediaPlayer();
        m = MediaPlayer.create(getApplicationContext(), R.raw.nadaalarm);

        try{
            m.prepare();

        } catch(Exception e){
            // handle error here..
            m.start();

        }

        m.setLooping(true);
        //   m.start();


        Button btn = (Button) findViewById(R.id.ok);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                m.stop();
                finish();
            }
        });

        TextView tvnamaalarm = (TextView) findViewById(R.id.namaalarm);
        tvnamaalarm.setText(judul);

        TextView tvacara = (TextView) findViewById(R.id.acara);
        tvacara.setText(isi);

        TextView tvgedung = (TextView) findViewById(R.id.gedung);
        tvgedung.setText(gedung + ", " +ruang);

        TextView tvorang = (TextView) findViewById(R.id.orang);
        tvorang.setText(orang);


    }
}
