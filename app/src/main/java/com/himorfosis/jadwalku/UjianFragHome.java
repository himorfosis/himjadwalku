package com.himorfosis.jadwalku;

import android.content.DialogInterface;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UjianFragHome extends Fragment {

    Fragment fragment;
    Database db;
    AlertDialog alertDialog;

    List<UjianClassData> datalist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.ujianfraghome, container, false);
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

        // get id from layout

        FloatingActionButton tambah = (FloatingActionButton) view.findViewById(R.id.fab);
        ListView listujianhome = (ListView) view.findViewById(R.id.listujianhome);
        ImageView gambarempty = (ImageView) view.findViewById(R.id.gambarempty);
        TextView kosong = (TextView) view.findViewById(R.id.kosong);

        // get data from database

        String data [] = null ;

        db = new Database(getActivity());
        datalist = db.getallujian();
        data = new String[datalist.size()];
        int i=0;   for (UjianClassData d : datalist) {
            data [i] = d.getId_ujian() + d.getUjian() + d.getTanggal() + d.getJammasuk() +  d.getGedung() + d.getRuang() + d.getDosen() + d.getPengingat();
            i++;

        }

        if (datalist.isEmpty()) {

            kosong.setVisibility(View.VISIBLE);
            gambarempty.setVisibility(View.VISIBLE);

        } else {

            Collections.sort(datalist, new Comparator<UjianClassData>() {

                @Override
                public int compare(UjianClassData jam1, UjianClassData jam2) {

                    return 0;
                }
            });

        }

        UjianListAdapter adapter = new UjianListAdapter(getActivity(), datalist);
        listujianhome.setAdapter(adapter);

        listujianhome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
                String[] data = {"Perbarui", "Hapus"};

                final UjianClassData position = datalist.get(i);

                build.setItems(data, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putInt("id", position.getId_ujian());
                                bundle.putString("acara", position.getUjian());
                                bundle.putString("tanggal", position.getTanggal());
                                bundle.putString("jamawal", position.getJammasuk());
                                bundle.putString("gedung", position.getGedung());
                                bundle.putString("ruang", position.getRuang());
                                bundle.putString("orang", position.getDosen());
                                bundle.putString("pengingat", position.getPengingat());

                                Fragment f = new UjianFragIsiData();
                                f.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();

                                break;

                            case 1:

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setTitle("Hapus Ujian");
                                builder.setMessage(position.getUjian() + ", Apakah anda yakin ?");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {

                                        db.deleteujian(String.valueOf(position.getId_ujian()));
                                        fragment = new UjianFragHome();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.frame_container, fragment);
                                        ft.commit();

                                        Toast.makeText(getActivity(), "data berhasil terhapus", Toast.LENGTH_SHORT).show();


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

                alertDialog = build.create();
                alertDialog.show();
                return true;
            }
        });


        listujianhome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UjianClassData p = datalist.get(i);
                Bundle bundle = new Bundle();
                bundle.putInt("id", p.getId_ujian());
                bundle.putString("ujian", p.getUjian());
                bundle.putString("tanggal", p.getTanggal());
                bundle.putString("jam", p.getJammasuk());
                bundle.putString("gedung", p.getGedung());
                bundle.putString("ruang", p.getRuang());
                bundle.putString("dosen", p.getDosen());
                bundle.putString("pengingat", p.getPengingat());

                Fragment f = new UjianFragLihatData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "ujian").addToBackStack("ujian").commit();
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragment = new UjianFragIsiData();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, fragment);
                ft.commit();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


    }

}
