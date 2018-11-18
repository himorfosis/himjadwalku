package com.himorfosis.jadwalku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TodayListAdapter extends BaseAdapter {

    private Context context;
    private List<TodayClassData> list;

    public TodayListAdapter(Context context, List<TodayClassData> list) {

//        super(context, R.layout.rowkegiatan, list);
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size(); //returns total of items in the list
    }


    @Override
    public Object getItem(int position) {
        return list.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            LayoutInflater layoutInflater;

            layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.rowtoday, null);

        }

        TodayClassData data = list.get(position);

        if (data != null ) {

            ImageView gambar = view.findViewById(R.id.gambarkegiatan);
            TextView namakegiatan = view.findViewById(R.id.namakegiatan);
            TextView orang = view.findViewById(R.id.orang);
            TextView lokasi = view.findViewById(R.id.lokasi);
            TextView jam = view.findViewById(R.id.waktu);

            String fitur = data.getFitur();

            if(fitur.equals("Kuliah")) {

                gambar.setImageResource(R.drawable.kuliah);
                lokasi.setText(data.getLokasi() + ", " +data.getRuang());


            } else if (fitur.equals("Tugas")) {

                gambar.setImageResource(R.drawable.tugas);
                lokasi.setText(data.getLokasi());


            } else if (fitur.equals("Ujian")) {

                gambar.setImageResource(R.drawable.ujian);
                lokasi.setText(data.getLokasi() + ", " +data.getRuang());


            } else {

                gambar.setImageResource(R.drawable.agenda);
                lokasi.setText(data.getLokasi() + ", " +data.getRuang());


            }

            namakegiatan.setText(data.getAcara());
            orang.setText(data.getOrang());
            jam.setText(data.getJammasuk());

        }

        return view;
    }

}
