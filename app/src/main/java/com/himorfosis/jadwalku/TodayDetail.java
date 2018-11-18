package com.himorfosis.jadwalku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodayDetail extends AppCompatActivity {

    TextView acara, tanggal, lokasi, ruang, orang, pengingat;
    String getid, getacara, gettanggal, getlokasi, getruang, getorang, getpengingat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaydetail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView toolbartext = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);

        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        kembali.setVisibility(View.VISIBLE);

        toolbartext.setText("Detail Kegiatan");

        acara = findViewById(R.id.acara);
        tanggal = findViewById(R.id.tanggal);
        lokasi = findViewById(R.id.lokasi);
        ruang = findViewById(R.id.ruangan);
        orang = findViewById(R.id.orang);
        pengingat = findViewById(R.id.pengingat);

        Intent bundle = getIntent();

        getacara = bundle.getStringExtra("acara");
        gettanggal = bundle.getStringExtra("tanggal");
        getlokasi = bundle.getStringExtra("lokasi");
        getruang = bundle.getStringExtra("ruang");
        getorang = bundle.getStringExtra("orang");
        getpengingat = bundle.getStringExtra("pengingat");

        acara.setText(getacara);
        tanggal.setText(gettanggal);
        lokasi.setText(getlokasi);
        ruang.setText(getruang);
        orang.setText(getorang);
        pengingat.setText(getpengingat);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(TodayDetail.this, Utama.class);
                startActivity(in);

            }
        });

    }

}
