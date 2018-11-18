package com.himorfosis.jadwalku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UjianFragLihatData extends Fragment {

    Fragment fragment;
    Database db;
    Integer id;
    String ujian, waktu, jam, gedung, ruang, dosen, pengingat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.ujianfraglihatdata, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        db = new Database(getActivity());

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.VISIBLE);

        TextView tvmatakuliah = (TextView) view.findViewById(R.id.matakuliah);
        TextView tvwaktu = (TextView) view.findViewById(R.id.waktu);
        TextView tvgedung = (TextView) view.findViewById(R.id.gedung);
        TextView tvruang = (TextView) view.findViewById(R.id.ruang);
        TextView tvdosen = (TextView) view.findViewById(R.id.dosen);
        TextView tvpengingat= (TextView) view.findViewById(R.id.pengingat);

        savedInstanceState = getArguments();
        if(savedInstanceState == null){


        }else {
            id = savedInstanceState.getInt("id");
            ujian = savedInstanceState.getString("ujian");
            waktu = savedInstanceState.getString("tanggal");
            jam = savedInstanceState.getString("jam");
            gedung = savedInstanceState.getString("gedung");
            ruang = savedInstanceState.getString("ruang");
            dosen = savedInstanceState.getString("dosen");
            pengingat = savedInstanceState.getString("pengingat");
        }


        Calendar cal = Calendar.getInstance();
        String[] daysArray = new String[] {"","Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
        String[] monthArray = new String[] {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

        String tanggalbulan = waktu.substring(waktu.indexOf("-")+1);
        final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-")+1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        final String tahun =waktu.substring(0, waktu.indexOf("-"));

        Log.d("testingssss", "-"+ tanggal);

        Date dateawal = new Date();
        try {
            dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(waktu);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(dateawal);

        int ihari = cal.get(Calendar.DAY_OF_WEEK);
        int intbulan = Integer.parseInt(bulan);

        // final String fixtanggal = daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun;


        tvmatakuliah.setText(ujian);
        tvwaktu.setText(daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun+ " \nJam : " +jam);
        tvgedung.setText(gedung);
        tvruang.setText(ruang);
        tvdosen.setText(dosen);
        tvpengingat.setText(pengingat);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new UjianFragHome();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, fragment);
                ft.commit();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("acara", ujian);
                bundle.putString("tanggal",waktu);
                bundle.putString("jamawal",jam);
                bundle.putString("gedung",gedung);
                bundle.putString("ruang", ruang);
                bundle.putString("orang", dosen);
                bundle.putString("pengingat", pengingat);

                Fragment f = new UjianFragIsiData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();

            }
        });


    }

}
