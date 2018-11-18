package com.himorfosis.jadwalku;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TugasFragLihatData extends Fragment {

    Fragment fragment;
    Database db;
    String id, deskripsi, tugas, waktu, jam, gedung,ruang,dosen,pengingat;
    byte[] bytegambar;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tugasfraglihatdata, container, false);
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

        TextView tvdeskripsi = (TextView) view.findViewById(R.id.deskripsi);
        TextView tvmatakuliah = (TextView) view.findViewById(R.id.matakuliah);
        TextView tvwaktu = (TextView) view.findViewById(R.id.waktu);
        TextView tvgedung = (TextView) view.findViewById(R.id.gedung);
        TextView tvdosen = (TextView) view.findViewById(R.id.dosen);
        TextView tvpengingat = (TextView) view.findViewById(R.id.pengingat);
        ImageView gambartugas = (ImageView) view.findViewById(R.id.gambar);

        savedInstanceState = getArguments();
        if(savedInstanceState==null){

        } else {

            id = savedInstanceState.getString("id");

        }

        db = new Database(getActivity());

        Cursor cursor = db.gettugas("SELECT * FROM tabeltugas");

        while (cursor.moveToNext()) {
            int idtugas = cursor.getInt(0);
            String dbtugas = cursor.getString(1);
            String dbketerangan = cursor.getString(2);
            String dbtanggal = cursor.getString(3);
            String dbjammasuk = cursor.getString(4);
            String dbgedung = cursor.getString(5);
            String dbdosen = cursor.getString(6);
            byte[] dbgambar = cursor.getBlob(7);
            String dbpengingat = cursor.getString(8);

            if (id.equals(String.valueOf(idtugas))) {

                tugas = dbtugas;
                deskripsi = dbketerangan;
                waktu = dbtanggal;
                jam = dbjammasuk;
                gedung = dbgedung;
                dosen = dbdosen;
                bytegambar = dbgambar;
                pengingat = dbpengingat;

                Log.e("gambar", "" +dbgambar);
                Log.e("gambar", "" +bytegambar);


                // convert byte to image

                gambartugas.setImageBitmap(BitmapFactory.decodeByteArray( dbgambar, 0, dbgambar.length));

            }
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
        Log.d("testingssss", "-"+ ihari);

        // final String fixtanggal = daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun;

        tvdeskripsi.setText(deskripsi);
        tvmatakuliah.setText(tugas);
        tvwaktu.setText(daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun +"\nJam : "+jam);
        tvgedung.setText(gedung);
        tvdosen.setText(dosen);
        tvpengingat.setText(pengingat);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TugasFragHome();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, fragment);
                ft.commit();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TugasIsiData.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });

    }

}
