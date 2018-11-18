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

public class KuliahFragLihatData extends Fragment {

    Fragment fragment;
    Database db;
    Integer id;
    String matkul, tanggal, gedung, ruang, orang, pengingat, formattedDate1, formattedDate2, jammasuk;
    String tglawal, tglakhir;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.kuliahfraglihatdata, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.VISIBLE);

        TextView tvmatakuliah = (TextView) view.findViewById(R.id.matakuliah);
        TextView tvwaktu = (TextView) view.findViewById(R.id.tanggal);
        TextView tvgedung = (TextView) view.findViewById(R.id.gedung);
        TextView tvruang = (TextView) view.findViewById(R.id.ruang);
        TextView tvdosen = (TextView) view.findViewById(R.id.dosen);
        TextView tvpengingat = (TextView) view.findViewById(R.id.pengingat);

        savedInstanceState = getArguments();
        if(savedInstanceState==null){

        }else {

            id = savedInstanceState.getInt("id");
            matkul = savedInstanceState.getString("matkul");
            formattedDate1 = savedInstanceState.getString("tanggalmulai");
            formattedDate2 = savedInstanceState.getString("tanggalselesai");
            jammasuk = savedInstanceState.getString("jamawal");
            gedung = savedInstanceState.getString("gedung");
            ruang = savedInstanceState.getString("ruang");
            orang = savedInstanceState.getString("orang");
            pengingat = savedInstanceState.getString("pengingat");

        }

        tglawal();

        tglakhir();

        Log.d("tanggal", "-"+ tanggal);

        tvmatakuliah.setText(matkul);
        tvwaktu.setText("Jam : " +jammasuk + " \n"+ tglawal + " - " + tglakhir);
        tvgedung.setText(gedung);
        tvruang.setText(ruang);
        tvdosen.setText(orang);
        tvpengingat.setText(pengingat);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new KuliahFragHome();
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
                bundle.putString("matkul", matkul);
                bundle.putString("tanggalmulai", tglawal);
                bundle.putString("tanggalselesai", tglakhir);
                bundle.putString("jamawal", jammasuk);
                bundle.putString("gedung", gedung);
                bundle.putString("ruang", ruang);
                bundle.putString("orang",orang);
                bundle.putString("pengingat", pengingat);

                Fragment f = new KuliahFragIsiData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();


            }
        });

    }

    private void tglawal() {

        Calendar cal = Calendar.getInstance();
        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String tanggalbulan = formattedDate1.substring(formattedDate1.indexOf("-") + 1);
        final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        String tahun = formattedDate1.substring(0, formattedDate1.indexOf("-"));

        Log.d("tanggal", "-" + tanggal);

        Date dateawal = new Date();
        try {

            dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate1);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        cal.setTime(dateawal);

        int ihari = cal.get(Calendar.DAY_OF_WEEK);
        int intbulan = Integer.parseInt(bulan);
        Log.d("hari awal", "-" + ihari);

        tglawal =  daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun;

    }

    private void tglakhir() {

        Calendar cal = Calendar.getInstance();
        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String tanggalbulan = formattedDate2.substring(formattedDate2.indexOf("-") + 1);
        final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        String tahun = formattedDate2.substring(0, formattedDate2.indexOf("-"));

        Log.d("tanggal", "-" + tanggal);

        Date dateawal = new Date();
        try {

            dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate2);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        cal.setTime(dateawal);

        int ihari = cal.get(Calendar.DAY_OF_WEEK);
        int intbulan = Integer.parseInt(bulan);
        Log.d("hari awal", "-" + ihari);

        tglakhir = daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun;

    }

}
