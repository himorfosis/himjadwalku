package com.himorfosis.jadwalku;

import android.os.Bundle;
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

public class AgendaFragLihatData extends Fragment {

    Fragment fragment;
    Database db;

    String id, acara, waktu, jam, lokasi, ruang, orang, pengingat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.agendafraglihatdata, container, false);
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

        TextView tvacara = (TextView) view.findViewById(R.id.acara);
        TextView tvwaktu = (TextView) view.findViewById(R.id.waktu);
        TextView tvgedung = (TextView) view.findViewById(R.id.gedung);
        TextView tvruang = (TextView) view.findViewById(R.id.ruang);
        TextView tvorang = (TextView) view.findViewById(R.id.orang);
        TextView tvpengingat = (TextView) view.findViewById(R.id.pengingat);

        savedInstanceState = getArguments();
        if(savedInstanceState==null){

        }else {

            id = savedInstanceState.getString("id");
            acara = savedInstanceState.getString("acara");
            waktu = savedInstanceState.getString("tanggal");
            jam = savedInstanceState.getString("jam");
            lokasi = savedInstanceState.getString("lokasi");
            ruang = savedInstanceState.getString("ruang");
            orang = savedInstanceState.getString("orang");
            pengingat = savedInstanceState.getString("pengingat");

        }

        Calendar cal = Calendar.getInstance();
        String[] daysArray = new String[] {"","Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
        String[] monthArray = new String[] {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

        String tanggalbulan = waktu.substring(waktu.indexOf("-")+1);
        final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-")+1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        String tahun =waktu.substring(0, waktu.indexOf("-"));

        Log.d("tanggal", "-"+ tanggal);

        Date dateawal = new Date();
        try {
            dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(waktu);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(dateawal);

        int ihari = cal.get(Calendar.DAY_OF_WEEK);
        int intbulan = Integer.parseInt(bulan);
        Log.d("hari awal", "-"+ ihari);


        // final String fixtanggal = daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun;

        tvacara.setText(acara);
        tvwaktu.setText(daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun+"\n" +"Jam : " +jam);
        tvgedung.setText(lokasi);
        tvruang.setText(ruang);
        tvorang.setText(orang);
        tvpengingat.setText(pengingat);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new AgendaFragHome();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, fragment);
                ft.commit();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("acara", acara);
                bundle.putString("tanggal", tanggal);
                bundle.putString("jam", jam);
                bundle.putString("lokasi", lokasi);
                bundle.putString("ruang", ruang);
                bundle.putString("orang", orang);
                bundle.putString("pengingat", pengingat);

                Fragment f = new AgendaFragIsiData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();

            }
        });

    }

}
