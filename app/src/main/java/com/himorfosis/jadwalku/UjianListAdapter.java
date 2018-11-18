package com.himorfosis.jadwalku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UjianListAdapter  extends ArrayAdapter<UjianClassData> {

    private Context context;
    private List<UjianClassData> list;
    Database db;

    public UjianListAdapter(Context context, List<UjianClassData> list) {
        super(context, R.layout.row_all_list, list);
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
            v = vi.inflate(R.layout.row_all_list, null);

        }

        UjianClassData p = list.get(position);

        db = new Database(getContext());

        if (p != null) {

            TextView matakuliah = (TextView) v.findViewById(R.id.isikuliah);
            TextView waktu = (TextView) v.findViewById(R.id.isijam);
            TextView gedung = (TextView) v.findViewById(R.id.isigedung);
            TextView dosen = (TextView) v.findViewById(R.id.isidosen);

            Calendar cal = Calendar.getInstance();
            String[] daysArray = new String[] {"","Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
            String[] monthArray = new String[] {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

            String tanggalbulan = p.getTanggal().substring(p.getTanggal().indexOf("-")+1);
            final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-")+1);
            String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
            String tahun =p.getTanggal().substring(0, p.getTanggal().indexOf("-"));

            Date dateawal = new Date();
            try {
                dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(p.getTanggal());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(dateawal);
            int ihari = cal.get(Calendar.DAY_OF_WEEK);
            int intbulan = Integer.parseInt(bulan);

            //Log.d("testing", "-"+ ihari);

            final String fixtanggal = daysArray[ihari]+", "+tanggal+" "+monthArray[intbulan-1]+" "+tahun;

            matakuliah.setText(p.getUjian());
            dosen.setText(p.getDosen());
            waktu.setText(fixtanggal+" \n"+p.getJammasuk());
            gedung.setText(p.getGedung()+", "+p.getRuang());

        }
        return v;

    };
}
