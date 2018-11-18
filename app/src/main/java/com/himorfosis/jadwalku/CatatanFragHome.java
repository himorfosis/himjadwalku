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

import static java.security.AccessController.getContext;

public class CatatanFragHome extends Fragment {

    FloatingActionButton fab;
    Fragment fragment;
    AlertDialog alertDialog;

    List<CatatanClassData> datalist;
    Database db;
    CatatanListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.catatanfraghome, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout btnsimpan = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnsimpan);
        btnsimpan.setVisibility(View.INVISIBLE);

        FloatingActionButton tambah = (FloatingActionButton) view.findViewById(R.id.fab);
        ListView list = (ListView) view.findViewById(R.id.listcatatan);
        ImageView gambarempty = view.findViewById(R.id.gambarempty);
        TextView kosong = view.findViewById(R.id.kosong);

        // get data from database

        String data[] = null;
        db = new Database(getActivity());

        datalist = db.getallcatatan();
        data = new String[datalist.size()];
        int i = 0;
        for (CatatanClassData d : datalist) {
            data[i] = d.getId_catatan() + d.getJudul() + d.getJudul();
            i++;

        }

        if (datalist.isEmpty()) {

            gambarempty.setVisibility(View.VISIBLE);
            kosong.setVisibility(View.VISIBLE);

        }

        adapter = new CatatanListAdapter(getActivity(), datalist);

        Collections.reverse(datalist);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CatatanClassData p = datalist.get(position);
                String idcatatan = p.getId_catatan();
                String judul = p.getJudul();
                String isi = p.getIsi();
                Log.d("id", " = " + idcatatan + judul + isi);

                Bundle bundle = new Bundle();
                bundle.putString("judul", judul);
                bundle.putString("isi", isi);
                bundle.putString("id_catatan", idcatatan);

                Fragment f = new CatatanFragIsiData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "judul").addToBackStack("judul").commit();
            }
        });


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new CatatanFragIsiData();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, fragment);
                ft.commit();

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final CatatanClassData p = datalist.get(position);
                String judul = p.getJudul();

                builder.setTitle("Hapus Catatan");
                builder.setMessage(judul + ", Apakah anda akan menghapus catatan ?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        db.deletecatatan(p.getId_catatan());
                        fragment = new CatatanFragHome();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_container, fragment);
                        ft.commit();

                        Toast.makeText(getActivity(), "data berhasil dihapus", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

    }

}
