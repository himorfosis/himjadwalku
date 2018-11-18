package com.himorfosis.jadwalku;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.List;

public class AgendaFragHome extends Fragment {

    FloatingActionButton fab;
    Fragment fragment;
    Database db;
    AlertDialog alertDialog;

    List<AgendaClassData> datalist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.agendafraghome, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.INVISIBLE);

        FloatingActionButton tambah = (FloatingActionButton) view.findViewById(R.id.fab);
        TextView kosong = view.findViewById(R.id.kosong);
        ImageView gambarempty = view.findViewById(R.id.gambarempty);
        ListView listagenda = (ListView) view.findViewById(R.id.listagendahome);


        String data[] = null;
        db = new Database(getActivity());

        datalist = db.getallagenda();
        data = new String[datalist.size()];
        int i = 0;
        for (AgendaClassData d : datalist) {

            data[i] = d.getId_agenda() + d.getAcara() + d.getTanggal() + d.getJammasuk() +  d.getLokasi() + d.getRuang() + d.getOrang() + d.getPengingat();
            i++;

        }

        if (datalist.isEmpty()) {

            kosong.setVisibility(View.VISIBLE);
            gambarempty.setVisibility(View.VISIBLE);

        }

        final AgendaListAdapter adapter = new AgendaListAdapter(getActivity(), datalist);
        Collections.reverse(datalist);
        listagenda.setAdapter(adapter);

        listagenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AgendaClassData p = datalist.get(i);

                Log.e("posisi", "" + i);
                Log.e("id", "" +p.getId_agenda());

                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(p.getId_agenda()));
                bundle.putString("acara", p.getAcara());
                bundle.putString("tanggal", p.getTanggal());
                bundle.putString("jam", p.getJammasuk());
                bundle.putString("lokasi", p.getLokasi());
                bundle.putString("ruang", p.getRuang());
                bundle.putString("orang", p.getOrang());
                bundle.putString("pengingat", p.getPengingat());

                Fragment f = new AgendaFragLihatData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();
            }
        });

        listagenda.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] inidata = {"Perbarui", "Hapus"};

                final AgendaClassData p = datalist.get(i);

                builder.setItems(inidata, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putString("id", String.valueOf(p.getId_agenda()));
                                bundle.putString("acara", p.getAcara());
                                bundle.putString("tanggal", p.getTanggal());
                                bundle.putString("jam", p.getJammasuk());
                                bundle.putString("lokasi", p.getLokasi());
                                bundle.putString("ruang", p.getRuang());
                                bundle.putString("orang", p.getOrang());
                                bundle.putString("pengingat", p.getPengingat());

                                Fragment f = new AgendaFragIsiData();
                                f.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();

                                break;

                            case 1:

                                AlertDialog.Builder build = new AlertDialog.Builder(getActivity());

                                build.setTitle("Hapus Ujian");
                                build.setMessage(p.getAcara() + ", Apakah anda yakin ?");
                                build.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {


                                        db.deleteagenda(String.valueOf(p.getId_agenda()));
                                        fragment = new AgendaFragHome();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.frame_container, fragment);
                                        ft.commit();

                                        Toast.makeText(getActivity(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                build.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                                alertDialog = build.create();
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


                fragment = new AgendaFragIsiData();
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
