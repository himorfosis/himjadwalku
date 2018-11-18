package com.himorfosis.jadwalku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CatatanListAdapter extends ArrayAdapter<CatatanClassData> {

    private Context context;
    private List<CatatanClassData> list;

    public CatatanListAdapter(Context context, List<CatatanClassData> list) {
        super(context, R.layout.rowcatatan, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.rowcatatan, null);

        }

        CatatanClassData p = list.get(position);

        if (p != null) {

            TextView judul = (TextView) v.findViewById(R.id.tvjudullistcatatan);

            judul.setText(p.getJudul());
        }
        return v;

    };


}
