package com.himorfosis.jadwalku;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TugasFragHome extends Fragment {

    FloatingActionButton fab;
    Fragment fragment;
    Database db;
    AlertDialog alertDialog;

    List<TugasClassData> datalist = new ArrayList<>();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tugasfraghome, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.INVISIBLE);

        // get ID from layout

        FloatingActionButton tambah = (FloatingActionButton) view.findViewById(R.id.fab);
        ListView listtugashome = (ListView) view.findViewById(R.id.listtugashome);
        ImageView gambarempty = view.findViewById(R.id.gambarempty);
        TextView kosong = view.findViewById(R.id.kosong);

        // database

        db = new Database(getActivity());

        Cursor cursor = db.gettugas("SELECT * FROM tabeltugas");

        datalist.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tugas = cursor.getString(1);
            String keterangan = cursor.getString(2);
            String tanggal = cursor.getString(3);
            String jammasuk = cursor.getString(4);
            String gedung = cursor.getString(5);
            String dosen = cursor.getString(6);
            byte[] gambar = cursor.getBlob(7);
            String pengingat = cursor.getString(8);

            datalist.add(new TugasClassData(id, tugas, keterangan, tanggal, jammasuk, gedung, dosen, gambar, pengingat));

        }

        if (datalist.isEmpty()) {

            kosong.setVisibility(View.VISIBLE);
            gambarempty.setVisibility(View.VISIBLE);

        } else {

            Collections.reverse(datalist);


        }

        TugasListAdapter adapter = new TugasListAdapter(getActivity(), datalist);
        listtugashome.setAdapter(adapter);

        listtugashome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TugasClassData p = datalist.get(i);

                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(p.getId_tugas()));
                bundle.putString("jam", p.getJammasuk());

                Fragment f = new TugasFragLihatData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "tugas").addToBackStack("tugas").commit();

            }
        });

        listtugashome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                String[] inidata = {"Perbarui data", "Hapus data"};

                final TugasClassData data = datalist.get(i);

                builder.setItems(inidata, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:

                                Intent intent = new Intent(getActivity(), TugasIsiData.class);
                                intent.putExtra("id", data.getId_tugas());

                                startActivity(intent);

                                break;

                            case 1:

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setTitle("Hapus Ujian");
                                builder.setMessage(data.getTugas() + ", Apakah anda yakin ?");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {

                                        db.deletetugas(data.getId_tugas());
                                        fragment = new TugasFragHome();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.frame_container, fragment);
                                        ft.commit();

                                        Toast.makeText(getActivity(), "Data berhasil terhapus", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                                alertDialog = builder.create();
                                alertDialog.show();

                                break;

                        }
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });




        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TugasIsiData.class);
                intent.putExtra("id", "kosong");
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().finish();

                Intent in = new Intent(getActivity(), Utama.class);
                startActivity(in);

            }
        });

    }

}
