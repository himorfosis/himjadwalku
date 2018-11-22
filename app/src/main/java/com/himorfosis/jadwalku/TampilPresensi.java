package com.himorfosis.jadwalku;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TampilPresensi extends AppCompatActivity {

    AlertDialog alertDialog;
    Context context;

    String kegiatan, isi, tempat;

    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_presensi);

        db = new Database(TampilPresensi.this);

        Button berangkat = findViewById(R.id.berangkat);
        Button tidak = findViewById(R.id.tidak);
        TextView tvacara = (TextView) findViewById(R.id.acara);
        TextView tvgedung = (TextView) findViewById(R.id.gedung);


        kegiatan = getIntent().getStringExtra("kegiatan");
        isi = getIntent().getStringExtra("isi");
        tempat = getIntent().getStringExtra("tempat");


        MediaPlayer mPlayer = MediaPlayer.create(TampilPresensi.this, R.raw.nadanotifikasi);
        mPlayer.start();

//        TextView tvnamaalarm = (TextView) findViewById(R.id.namaalarm);
//        tvnamaalarm.setText(judul);

        tvacara.setText(isi);
        tvgedung.setText(tempat);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = df2.format(c.getTime());
        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String hari = formattedDate1.substring(formattedDate1.indexOf("-") + 1);
        String tanggalbulan = formattedDate1.substring(formattedDate1.indexOf("-") + 1);
        String strtanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        String tahun = formattedDate1.substring(0, formattedDate1.indexOf("-"));

        int intbulan = Integer.parseInt(bulan);
        final int date = c.get(Calendar.DAY_OF_WEEK);

        String tanggalsekarang = daysArray[date] + ", " + strtanggal + " " + monthArray[intbulan - 1] + " " + tahun;


        berangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.insertkehadiran(new KehadiranClassData(null, kegiatan, "1", tanggalsekarang));

                finish();


            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.insertkehadiran(new KehadiranClassData(null, kegiatan, "0", tanggalsekarang));

                finish();


            }
        });


    }


}
