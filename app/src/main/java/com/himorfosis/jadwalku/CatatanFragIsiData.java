package com.himorfosis.jadwalku;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CatatanFragIsiData extends Fragment {

    Fragment fragment;
    String id_catatan = null;
    String judulawal = null;
    String isiawal = null;
    AlertDialog alertDialog;
    Database db;
    EditText edjudul,edisi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.catatanfragisidata, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();
        savedInstanceState = getArguments();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout btnsimpan = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnsimpan);
        btnsimpan.setVisibility(View.VISIBLE);

        db = new Database(getActivity());

        edjudul = (EditText) view.findViewById(R.id.input_judul);
        edisi = (EditText) view.findViewById(R.id.input_isicatatan);

        if (savedInstanceState == null) {

        } else {

            id_catatan = savedInstanceState.getString("id_catatan");
            judulawal = savedInstanceState.getString("judul");
            isiawal = savedInstanceState.getString("isi");
            edjudul.setText(savedInstanceState.getString("judul"));
            edisi.setText(savedInstanceState.getString("isi"));

        }

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edjudul.getText().toString().length() == 0 || edisi.getText().toString().length() == 0) {

                    Toast.makeText(getActivity(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                } else {

                    if (judulawal == null || isiawal == null) {

                        Cursor c = db.cekcatatan(edjudul.getText().toString());
                        c.moveToFirst();
                        String sjumlah = db.cekcatatan0(c);
                        int jumlah = Integer.parseInt(sjumlah);

                        if (jumlah == 0) {

                            db.insertcatatan(new CatatanClassData(null, edjudul.getText().toString(), edisi.getText().toString()));
                            fragment = new CatatanFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();
                            Log.d("NULL2", "=");

                        } else {

                            Toast.makeText(getActivity(), "Judul catatan sudah ada", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        if (!judulawal.equals(edjudul.getText().toString())) {

                            Cursor c = db.cekcatatan(edjudul.getText().toString());
                            c.moveToFirst();
                            String sjumlah = db.cekcatatan0(c);
                            int jumlah = Integer.parseInt(sjumlah);

                            if (jumlah == 0) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setTitle("Perubahan");
                                builder.setMessage("Anda melakukan perubahan baru pada catatan, " + judulawal + "\nSimpan ?");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.updatetabelcatatan(id_catatan, edjudul.getText().toString(), edisi.getText().toString());
                                        getActivity().getSupportFragmentManager().popBackStack();
                                    }
                                });

                                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                                alertDialog = builder.create();
                                alertDialog.show();

                            } else {

                                Toast.makeText(getActivity(), "Judul catatan sudah ada", Toast.LENGTH_SHORT).show();

                            }

                        } else {

                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            builder.setTitle("Perubahan");
                            builder.setMessage("Anda melakukan perubahan baru pada catatan, " + judulawal + "\nSimpan ?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    db.updatetabelcatatan(id_catatan, edjudul.getText().toString(), edisi.getText().toString());
                                    Log.d("CARITAU", " = " + id_catatan + edjudul.getText().toString() + edisi.getText().toString());
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                            });

                            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                            alertDialog = builder.create();
                            alertDialog.show();

                        }
                    }
                }

            }
        });

        // tombol kembali di action bar

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (judulawal == null || isiawal == null) {

                    Log.d("NULL", "=");

                    if (edjudul.getText().toString().length() > 0 || edisi.getText().toString().length() > 0) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setTitle("Peringatan");
                        builder.setMessage("Apakah anda ingin melanjutkan isi data ?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                fragment = new CatatanFragHome();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.frame_container, fragment);
                                ft.commit();

                            }
                        });

                        alertDialog = builder.create();
                        alertDialog.show();

                    } else {

                        fragment = new CatatanFragHome();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_container, fragment);
                        ft.commit();
                        Log.d("NULL2", "=");

                    }

                } else {

                    if (!judulawal.equals(edjudul.getText().toString()) || !isiawal.equals(edisi.getText().toString())) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setTitle("Peringatan");
                        builder.setMessage("Anda melakukan perubahan baru pada catatan, lanjutkan ?");

                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                getActivity().getSupportFragmentManager().popBackStack();

                            }
                        });

                        alertDialog = builder.create();
                        alertDialog.show();

                    } else {

                        getActivity().getSupportFragmentManager().popBackStack();

                    }
                }

            }
        });

    }

}
