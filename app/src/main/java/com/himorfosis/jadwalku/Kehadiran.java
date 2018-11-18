package com.himorfosis.jadwalku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Random;

public class Kehadiran extends AppCompatActivity {

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

    ArrayList<BarEntry> data = new ArrayList<>();
    ArrayList<BarEntry> barkuliah = new ArrayList<>();
    ArrayList<BarEntry> baragenda = new ArrayList<>();
    ArrayList<BarEntry> bartugas = new ArrayList<>();
    ArrayList<BarEntry> barujian = new ArrayList<>();

    ArrayList<Integer> kuliah = new ArrayList<>();
    ArrayList<Integer> agenda = new ArrayList<>();
    ArrayList<Integer> tugas = new ArrayList<>();
    ArrayList<Integer> ujian = new ArrayList<>();

    ArrayList<BarDataSet> dataSets = new ArrayList<>();

    TextView agendahadir, kuliahhadir, ujianhadir, tugashadir, agendabolos, kuliahbolos, ujianbolos, tugasbolos;

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

        kuliah.add(1);
        kuliah.add(2);
        kuliah.add(6);

        agenda.add(3);
        agenda.add(1);
        agenda.add(1);

        tugas.add(3);
        tugas.add(2);
        tugas.add(7);

        ujian.add(1);
        ujian.add(1);
        ujian.add(1);

        Integer jumlahkuliah = 0;
        Integer jumlahagenda = 0;
        Integer jumlahtugas = 0;
        Integer jumlahujian = 0;

        for (int i = 0; i < kuliah.size(); i++) {

            jumlahkuliah = jumlahkuliah + kuliah.get(i);

        }

        for (int i = 0; i < agenda.size(); i++) {

            jumlahagenda = jumlahagenda + agenda.get(i);

        }

        for (int i = 0; i < tugas.size(); i++) {

            jumlahtugas = jumlahtugas + tugas.get(i);

        }

        for (int i = 0; i < ujian.size(); i++) {

            jumlahujian = jumlahujian + ujian.get(i);

        }

        agendahadir.setText(String.valueOf(jumlahagenda));
        kuliahhadir.setText(String.valueOf(jumlahkuliah));
        ujianhadir.setText(String.valueOf(jumlahujian));
        tugashadir.setText(String.valueOf(jumlahtugas));

        float convkul = (float) jumlahkuliah;
        float convage = (float) jumlahagenda;
        float convtug = (float) jumlahtugas;
        float convuji = (float) jumlahujian;

        data.add(new BarEntry(convkul, 0));
        data.add(new BarEntry(convage, 1));
        data.add(new BarEntry(convtug, 2));
        data.add(new BarEntry(convuji, 3));

        BarDataSet barDataSet = new BarDataSet(data, "Data");

        ArrayList<String> kehadiran = new ArrayList<>();
        kehadiran.add("Kuliah");
        kehadiran.add("Agenda");
        kehadiran.add("Tugas");
        kehadiran.add("Ujian");


        BarData thedata = new BarData(kehadiran, barDataSet);
        barChart.setData(thedata);

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

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> kuliahValue = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        kuliahValue.add(v1e1);


        ArrayList<BarEntry> ujianValue = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(10.000f, 1); // Jan
        ujianValue.add(v2e1);


        ArrayList<BarEntry> tugasValue = new ArrayList<>();
        BarEntry tugas1 = new BarEntry(150.000f, 2); // Jan
        tugasValue.add(tugas1);

        ArrayList<BarEntry> agendaValue = new ArrayList<>();
        BarEntry agenda1 = new BarEntry(90.000f, 3); // Jan
        agendaValue.add(agenda1);

        BarDataSet kuliahBar = new BarDataSet(kuliahValue, "Kuliah");
        kuliahBar.setColor(Color.parseColor("#6DC38F"));

        BarDataSet ujianBar = new BarDataSet(ujianValue, "Tugas");
        ujianBar.setColor(Color.parseColor("#FDD362"));

        BarDataSet tugasBar = new BarDataSet(tugasValue, "Ujian");
        tugasBar.setColor(Color.parseColor("#EF4B4F"));

        BarDataSet agendaBar = new BarDataSet(agendaValue, "Agenda");
        agendaBar.setColor(Color.parseColor("#00AFEF"));

        dataSets = new ArrayList<>();
        dataSets.add(kuliahBar);
        dataSets.add(ujianBar);
        dataSets.add(tugasBar);
        dataSets.add(agendaBar);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> bulan = new ArrayList<>();
        bulan.add("Kuliah");
        bulan.add("Agenda");
        bulan.add("Tugas");
        bulan.add("Ujian");

        return bulan;

    }

    public void createRandomBarGraph(String Date1, String Date2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1, mDate2);

            barEntries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random();
            for (int j = 0; j < dates.size(); j++) {
                max = 100f;
                value = random.nextFloat() * max;
                barEntries.add(new BarEntry(value, j));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        BarData barData = new BarData(dates, barDataSet);
        barChart.setData(barData);
        barChart.setDescription("My First Bar Graph!");

    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<String>();
        while (startDate.compareTo(endDate) <= 0) {
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    public String getDate(Calendar cld) {
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH);

        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate = new SimpleDateFormat("yyy/MM/dd").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curDate;
    }

}
