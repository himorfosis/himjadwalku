package com.himorfosis.jadwalku;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabPengaturan extends Fragment {

    String[] select = {"Default", "wakeup", "bossku", "Besok", "Sidang", "Skripsi"};
    String[] getar = {"Mati", "Default", "Pendek", "Lama"};
    String[] durasi = {"Default", "15 menit", "30 menit", "60 menit", "90 menit", "120 menit"};

    int pilihalarm, pilihdurasi;

    int presensi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tabpengaturan, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pengaturan");

        LinearLayout nadaalarm = (LinearLayout) view.findViewById(R.id.nadaalarm);
        LinearLayout nadanotif = (LinearLayout) view.findViewById(R.id.nadanotif);
        TextView isidurasi = (TextView) view.findViewById(R.id.isidurasi);
        LinearLayout pilihgetaralarm = view.findViewById(R.id.pilihgetaralarm);
        LinearLayout pilihgetarnotif = view.findViewById(R.id.pilihgetarnotif);
        LinearLayout durasipresensi = view.findViewById(R.id.durasipresensi);

        presensi = SharedPref.getIntPref("durasipresensi", "presensi", getActivity());

        isidurasi.setText(durasi[presensi]);


        durasipresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(getContext())

                        .setTitle("Waktu presensi setelah kegiatan di mulai")
                        .setSingleChoiceItems(durasi, presensi, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pilihdurasi = which;

                            }
                        })


                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPref.saveIntPref("durasipresensi", "presensi", pilihdurasi, getContext());

                                int presensi = SharedPref.getIntPref("durasipresensi", "presensi", getActivity());

                                isidurasi.setText(durasi[presensi]);

                            }
                        })

                        .create();
                dialog.show();


            }
        });


        nadaalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int intCheck = SharedPref.getIntPref("alarmnada", "alarm", getContext());

                AlertDialog dialog = new AlertDialog.Builder(getContext())

                        .setTitle("Nada Alarm")
                        .setSingleChoiceItems(select, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pilihalarm = which;

                            }
                        })


                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPref.saveIntPref("alarmnada", "alarm", pilihalarm, getContext());

                            }
                        })

                        .create();
                dialog.show();

            }
        });

        nadanotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int intCheck = SharedPref.getIntPref("notifnada", "notif", getContext());

                AlertDialog dialog = new AlertDialog.Builder(getContext())

                        .setTitle("Nada Alarm")
                        .setSingleChoiceItems(select, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pilihalarm = which;

                            }
                        })


                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPref.saveIntPref("notifnada", "notif", pilihalarm, getContext());

                            }
                        })

                        .create();
                dialog.show();

            }
        });

        pilihgetaralarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int intCheck = SharedPref.getIntPref("alarmnada", "alarm", getContext());

                AlertDialog dialog = new AlertDialog.Builder(getContext())

                        .setTitle("Nada Alarm")
                        .setSingleChoiceItems(getar, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pilihalarm = which;

                            }
                        })

                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                SharedPref.saveIntPref("alarmnada", "alarm", selectedFont, getContext());

                            }
                        })

                        .create();
                dialog.show();

            }
        });

        pilihgetarnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int intCheck = SharedPref.getIntPref("alarmnada", "alarm", getContext());

                AlertDialog dialog = new AlertDialog.Builder(getContext())

                        .setTitle("Nada Alarm")
                        .setSingleChoiceItems(getar, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pilihalarm = which;

                            }
                        })


                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                SharedPref.saveIntPref("alarmnada", "alarm", selectedFont, getContext());

                            }
                        })

                        .create();
                dialog.show();

            }
        });


    }
}
