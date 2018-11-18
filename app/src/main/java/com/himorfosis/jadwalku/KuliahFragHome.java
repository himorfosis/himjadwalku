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

public class KuliahFragHome extends Fragment {

    String[] daftar;
    Fragment fragment;
    Database db;
    AlertDialog alertDialog;

    List<KuliahClassData> datalist;
    ListView listkuliahhome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.kuliahfraghome, container, false);

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

        FloatingActionButton tambah = (FloatingActionButton) view.findViewById(R.id.fab);
        TextView kosong = (TextView) view.findViewById(R.id.kosong);
        ImageView gambarempty = (ImageView) view.findViewById(R.id.gambarempty);
        listkuliahhome = (ListView) view.findViewById(R.id.listkuliahhome);

        // get data from database

        String data[] = null;
        db = new Database(getActivity());

        datalist = db.getallkuliah();
        data = new String[datalist.size()];
        int i=0;   for (KuliahClassData d : datalist) {
            data [i] = d.getIdkuliah() + d.getMatakuliah() + d.getTanggalmulai() +d.getTanggalselesai() + d.getJammulai()  + d.getLokasi() + d.getRuang()  + d.getPengingat();
            i++;

        }

        if (datalist.isEmpty()) {

            kosong.setVisibility(View.VISIBLE);
            gambarempty.setVisibility(View.VISIBLE);

        } else {

            Collections.reverse(datalist);

//            Collections.sort(datalist, new Comparator<KuliahClassData>() {
//
//                @Override
//                public int compare(KuliahClassData jam1, KuliahClassData jam2) {
//
//                    return 0;
//                }
//            });

        }

        final KuliahListAdapter adapter = new KuliahListAdapter(getActivity(), datalist);
        listkuliahhome.setAdapter(adapter);


        listkuliahhome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] inidata = {"Perbarui", "Hapus"};

                final KuliahClassData data = datalist.get(i);

                builder.setItems(inidata, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putInt("id", data.getIdkuliah());
                                bundle.putString("matkul", data.getMatakuliah());
                                bundle.putString("tanggalmulai", data.getTanggalmulai());
                                bundle.putString("tanggalselesai", data.getTanggalselesai());
                                bundle.putString("jamawal", data.getJammulai());
                                bundle.putString("gedung", data.getLokasi());
                                bundle.putString("ruang", data.getRuang());
                                bundle.putString("orang", data.getDosen());
                                bundle.putString("pengingat", data.getPengingat());

                                Fragment f = new KuliahFragIsiData();
                                f.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "acara").addToBackStack("acara").commit();

                                break;

                            case 1:

                                AlertDialog.Builder build = new AlertDialog.Builder(getActivity());

                                build.setTitle("Hapus Kuliah");
                                build.setMessage(data.getMatakuliah() +", Apakah anda yakin ?");
                                build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        db.deletejadwalkuliah(String.valueOf(data.getIdkuliah()));
                                        fragment = new KuliahFragHome();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.frame_container, fragment);
                                        ft.commit();

                                        Toast.makeText(getActivity(), "data berhasil terhapus", Toast.LENGTH_SHORT).show();


                                        Log.e("Data di hapus", "= "+data.getIdkuliah());
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


        listkuliahhome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                KuliahClassData data = datalist.get(i);

                Bundle bundle = new Bundle();
                bundle.putInt("id", data.getIdkuliah());
                bundle.putString("matkul", data.getMatakuliah());
                bundle.putString("tanggalmulai", data.getTanggalmulai());
                bundle.putString("tanggalselesai", data.getTanggalselesai());
                bundle.putString("jamawal", data.getJammulai());
                bundle.putString("gedung", data.getLokasi());
                bundle.putString("ruang", data.getRuang());
                bundle.putString("orang", data.getDosen());
                bundle.putString("pengingat", data.getPengingat());

                Fragment f = new KuliahFragLihatData();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, f, "id_matakuliah").addToBackStack("id_matakuliah").commit();
            }
        });


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragment = new KuliahFragIsiData();
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
