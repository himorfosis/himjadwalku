package com.himorfosis.jadwalku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Kehadiran extends AppCompatActivity {

    BarChart barChart;
    Database db;

    List<KehadiranClassData> kehadiran = new ArrayList<>();

    ArrayList<BarEntry> data = new ArrayList<>();
    ArrayList<BarEntry> databolos = new ArrayList<>();


    ArrayList<Integer> kuliah = new ArrayList<>();
    ArrayList<Integer> agenda = new ArrayList<>();
    ArrayList<Integer> tugas = new ArrayList<>();
    ArrayList<Integer> ujian = new ArrayList<>();

    ArrayList<BarDataSet> dataSets = new ArrayList<>();

    TextView agendahadir, kuliahhadir, ujianhadir, tugashadir, agendabolos, kuliahbolos, ujianbolos, tugasbolos;

    Integer jmlkuliah= 0;
    Integer jmlujian = 0;
    Integer jmltugas = 0;
    Integer jmlagenda = 0;
    Integer blskuliah = 0;
    Integer blsujian = 0;
    Integer blstugas = 0;
    Integer blsagenda = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kehadiran);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView toolbartext = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        toolbartext.setText("Kehadiran");

        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        barChart = (BarChart) findViewById(R.id.chart);
        agendahadir = findViewById(R.id.agendahadir);
        tugashadir = findViewById(R.id.tugashadir);
        ujianhadir = findViewById(R.id.ujianhadir);
        kuliahhadir = findViewById(R.id.kuliahhadir);
        agendabolos = findViewById(R.id.agendabolos);
        tugasbolos = findViewById(R.id.tugasbolos);
        kuliahbolos = findViewById(R.id.kuliahbolos);
        ujianbolos = findViewById(R.id.ujianbolos);

        db = new Database(Kehadiran.this);

        kehadiran = db.getKehadiran();

        if (kehadiran.isEmpty()) {

            Log.e("data ", "kosong");

            BarData thedata = new BarData(dataKegiatan(), getDataKosong());
            barChart.setData(thedata);
            barChart.animateXY(2000, 2000);
            barChart.invalidate();

        } else {

            BarData thedata = new BarData(dataKegiatan(), getDataSet());
            barChart.setData(thedata);
//            barChart.setDescription("Kehadiran");
            barChart.animateXY(2000, 2000);
            barChart.invalidate();

        }


        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Kehadiran.this, Utama.class);
                startActivity(in);

            }
        });

    }

    private ArrayList<String> dataKegiatan() {

        ArrayList<String> kehadiran = new ArrayList<>();
        kehadiran.add("Kuliah");
        kehadiran.add("Agenda");
        kehadiran.add("Tugas");
        kehadiran.add("Ujian");

        return kehadiran;

    }

    private ArrayList<BarDataSet> getDataSet() {


        for (int i = 0; i < kehadiran.size(); i++) {

            KehadiranClassData d = kehadiran.get(i);

            if (d.getKegiatan().equals("Kuliah")) {

                if (d.getStatus().equals("1")) {

                    jmlkuliah = jmlkuliah + 1;

                } else {

                    blskuliah = blskuliah + 1;

                }

            } else if (d.getKegiatan().equals("Tugas")) {

                if (d.getStatus().equals("1")) {

                    jmltugas = jmltugas + 1;

                } else {

                    blstugas = blstugas + 1;

                }

            } else if (d.getKegiatan().equals("Ujian")) {

                if (d.getStatus().equals("1")) {

                    jmlujian = jmlujian + 1;

                } else {

                    blsujian = blsujian + 1;

                }

            } else {

                if (d.getStatus().equals("1")) {

                    jmlagenda = jmlagenda + 1;

                } else {

                    blsagenda = blsagenda + 1;

                }

            }

        }

        kuliah.add(jmlkuliah);
        kuliahhadir.setText(String.valueOf(jmlkuliah));
        kuliahbolos.setText(String.valueOf(blskuliah));

        agenda.add(jmlagenda);
        agendahadir.setText(String.valueOf(jmlagenda));
        agendabolos.setText(String.valueOf(blsagenda));

        tugas.add(jmltugas);
        tugashadir.setText(String.valueOf(jmltugas));
        tugasbolos.setText(String.valueOf(blstugas));

        ujian.add(jmlujian);
        ujianhadir.setText(String.valueOf(jmlujian));
        ujianbolos.setText(String.valueOf(jmlujian));

        float convkul = (float) blskuliah;
        float convage = (float) blsagenda;
        float convtug = (float) blstugas;
        float convuji = (float) blsujian;

        float convkulbls = (float) jmlkuliah;
        float convagebls = (float) jmlagenda;
        float convtugbls = (float) jmltugas;
        float convujibls = (float) jmlujian;

        data.add(new BarEntry(convkul, 0));
        data.add(new BarEntry(convage, 1));
        data.add(new BarEntry(convtug, 2));
        data.add(new BarEntry(convuji, 3));

        databolos.add(new BarEntry(convkulbls, 0));
        databolos.add(new BarEntry(convagebls, 1));
        databolos.add(new BarEntry(convtugbls, 2));
        databolos.add(new BarEntry(convujibls, 3));


        BarDataSet barDataHadir = new BarDataSet(data, "Hadir");
        barDataHadir.setColor(Color.parseColor("#6DC38F"));
        BarDataSet barDataBolos= new BarDataSet(databolos, "Tidak Hadir");
        barDataBolos.setColor(Color.parseColor("#f11435"));

        dataSets = new ArrayList<>();
        dataSets.add(barDataHadir);
        dataSets.add(barDataBolos);

        return dataSets;
    }

    private ArrayList<BarDataSet> getDataKosong() {

        kuliah.add(jmlkuliah);
        kuliahhadir.setText(String.valueOf(jmlkuliah));
        kuliahbolos.setText(String.valueOf(blskuliah));

        agenda.add(jmlagenda);
        agendahadir.setText(String.valueOf(jmlagenda));
        agendabolos.setText(String.valueOf(blsagenda));

        tugas.add(jmltugas);
        tugashadir.setText(String.valueOf(jmltugas));
        tugasbolos.setText(String.valueOf(blstugas));

        ujian.add(jmlujian);
        ujianhadir.setText(String.valueOf(jmlujian));
        ujianbolos.setText(String.valueOf(jmlujian));

        float convkul = (float) blskuliah;
        float convage = (float) blsagenda;
        float convtug = (float) blstugas;
        float convuji = (float) blsujian;

        float convkulbls = (float) jmlkuliah;
        float convagebls = (float) jmlagenda;
        float convtugbls = (float) jmltugas;
        float convujibls = (float) jmlujian;

        data.add(new BarEntry(convkul, 0));
        data.add(new BarEntry(convage, 1));
        data.add(new BarEntry(convtug, 2));
        data.add(new BarEntry(convuji, 3));

        databolos.add(new BarEntry(convkulbls, 0));
        databolos.add(new BarEntry(convagebls, 1));
        databolos.add(new BarEntry(convtugbls, 2));
        databolos.add(new BarEntry(convujibls, 3));


        BarDataSet barDataHadir = new BarDataSet(data, "Hadir");
        barDataHadir.setColor(Color.parseColor("#6DC38F"));
        BarDataSet barDataBolos= new BarDataSet(databolos, "Tidak Hadir");
        barDataBolos.setColor(Color.parseColor("#f11435"));

        dataSets = new ArrayList<>();
        dataSets.add(barDataHadir);
        dataSets.add(barDataBolos);

        return dataSets;
    }

}
