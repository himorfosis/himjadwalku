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

public class TugasListAdapter extends ArrayAdapter<TugasClassData> {

    private Context context;
    private List<TugasClassData> list;
    Database db;

    public TugasListAdapter(Context context, List<TugasClassData> list) {
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

        TugasClassData p = list.get(position);

        db = new Database(getContext());

        if (p != null) {

            TextView matakuliah = (TextView) v.findViewById(R.id.isikuliah);
            TextView waktu = (TextView) v.findViewById(R.id.isijam);
            TextView gedung = (TextView) v.findViewById(R.id.isigedung);
            TextView dosen = (TextView) v.findViewById(R.id.isidosen);

           /*
            Cursor c = db.getmatakuliah(p.getid_matakuliah());
            c.moveToFirst();
            String mk = db.getmatakuliah1(c);
            String dsn = db.getmatakuliah2(c);
            matakuliah.setText(p.getid_tugas());
            dosen.setText(p.getdosen());

             */


            matakuliah.setText(p.getTugas());
            dosen.setText(p.getDosen());

            Calendar cal = Calendar.getInstance();
            String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
            String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

            String tanggalbulan = p.getTanggal().substring(p.getTanggal().indexOf("-") + 1);
            final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
            String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
            String tahun = p.getTanggal().substring(0, p.getTanggal().indexOf("-"));

            Date dateawal = new Date();
            try {
                dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(p.getTanggal());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(dateawal);
            int ihari = cal.get(Calendar.DAY_OF_WEEK);
            int intbulan = Integer.parseInt(bulan);


            final String fixtanggal = daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun;

            waktu.setText(fixtanggal + " \n" + p.getJammasuk());
            gedung.setText(p.getGedung());
        }
        return v;

    }

}
