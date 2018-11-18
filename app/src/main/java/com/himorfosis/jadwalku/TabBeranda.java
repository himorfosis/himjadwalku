package com.himorfosis.jadwalku;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TabBeranda extends Fragment implements AbsListView.OnScrollListener {

    Fragment fragment;
    Database db;
    AlertDialog alertDialog;

    LinearLayout kuliah, tugas, ujian, catatan, agenda, kehadiran;
    TextView tanggal;

    FrameLayout frameLayout;

    LayoutInflater inflater;
    ListView list;

    List<TodayClassData> datatoday = new ArrayList<>();
    List<KuliahClassData> datakuliah = new ArrayList<>();
    List<UjianClassData> dataujian = new ArrayList<>();
    List<AgendaClassData> dataagenda = new ArrayList<>();

    private int lastTopValue = 0;

    String tanggalsekarang, fixtanggal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tabberanda, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Beranda");

        list = (ListView) view.findViewById(R.id.list);
        TextView kosong = view.findViewById(R.id.kosong);

        // cek tanggal

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = df2.format(c.getTime());
        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String hari = formattedDate1.substring(formattedDate1.indexOf("-") + 1);
        String tanggalbulan = formattedDate1.substring(formattedDate1.indexOf("-") + 1);
        String strtanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        String tahun = formattedDate1.substring(0, formattedDate1.indexOf("-"));

        int intbulan = Integer.parseInt(bulan);
        final int date = c.get(Calendar.DAY_OF_WEEK);

        tanggalsekarang = daysArray[date] + ", " + strtanggal + " " + monthArray[intbulan - 1] + " " + tahun;

        // get kegiatan hari ini

        db = new Database(getActivity());

        kuliah();

        tugas();

        ujian();

        agenda();

        Log.e("data today", "" +datatoday);

        if (datatoday == null || datatoday.isEmpty()) {

            kosong.setVisibility(View.VISIBLE);

        }

        View header = (View) getActivity().getLayoutInflater().inflate(R.layout.tabberandacontent,null);
        list.addHeaderView(header);

        Collections.sort(datatoday, new Comparator<TodayClassData>() {

            @Override
            public int compare(TodayClassData jam1, TodayClassData jam2) {
                try {
                    return new SimpleDateFormat("hh:mm").parse(jam1.getJammasuk()).compareTo(new SimpleDateFormat("hh:mm").parse(jam2.getJammasuk()));

                } catch (ParseException e) {
                    return 0;

                }
            }
        });


        TodayListAdapter adapter = new TodayListAdapter(getActivity(), datatoday);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                int pos = 1 - position;

                Log.e("posisi", "" +position);

                try {

                    TodayClassData data = datatoday.get(position);

                    String tanggal = data.getTanggal() + ", " + data.getJammasuk();

                    Intent intent = new Intent(getActivity(), TodayDetail.class);
                    intent.putExtra("acara", data.getAcara());
                    intent.putExtra("tanggal", tanggal);
                    intent.putExtra("lokasi", data.getLokasi());
                    intent.putExtra("ruang", data.getRuang());
                    intent.putExtra("orang", data.getOrang());
                    intent.putExtra("pengingat", data.getPengingat());

                    startActivity(intent);


                } catch (Exception e) {

                    Log.e("Error", "" +e);
                    Toast.makeText(getActivity(), "Error Bos", Toast.LENGTH_SHORT).show();

                }
            }
        });


        // we take the background image and button reference from the header

        frameLayout = header.findViewById(R.id.frameberanda);
        kuliah = (LinearLayout) header.findViewById(R.id.kuliah);
        tugas = (LinearLayout) header.findViewById(R.id.tugas);
        ujian = (LinearLayout) header.findViewById(R.id.ujian);
        catatan = (LinearLayout) header.findViewById(R.id.catatan);
        agenda = (LinearLayout) header.findViewById(R.id.agenda);
        kehadiran = (LinearLayout) header.findViewById(R.id.kehadiran);
        tanggal = (TextView) header.findViewById(R.id.tanggal);

        tanggal.setText(tanggalsekarang);

        kuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent kuliah = new Intent(getActivity(), Kuliah.class);
                startActivity(kuliah);
                //Toast.makeText(getActivity(), "Kuliah di klik", Toast.LENGTH_SHORT).show();

            }
        });

        tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tugas = new Intent(getActivity(), Tugas.class);
                startActivity(tugas);
                // Toast.makeText(getActivity(), "Tugas di klik", Toast.LENGTH_SHORT).show();


            }
        });


        ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ujian = new Intent(getActivity(), Ujian.class);
                startActivity(ujian);
                // Toast.makeText(getActivity(), "Ujian di klik", Toast.LENGTH_SHORT).show();


            }
        });

        catatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent catatan = new Intent(getActivity(), Catatan.class);
                startActivity(catatan);
                // Toast.makeText(getActivity(), "Catatan di klik", Toast.LENGTH_SHORT).show();


            }
        });


        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent agenda = new Intent(getActivity(), Agenda.class);
                startActivity(agenda);
                //Toast.makeText(getActivity(), "Agenda di klik", Toast.LENGTH_SHORT).show();


            }
        });

        kehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Kehadiran.class);
                startActivity(intent);

            }
        });

        list.setOnScrollListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Rect rect = new Rect();
        frameLayout.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {

            lastTopValue = rect.top;
            frameLayout.setY((float) (rect.top / 2.0));

        }
    }


    private void kuliah() {

        Log.e("kuliah", "kuliah");

        datakuliah = db.getallkuliah();

        for (int i = 0; i < datakuliah.size(); i++) {

            KuliahClassData d = datakuliah.get(i);

            aturtanggal(d.getTanggalmulai());

            if (tanggalsekarang.equals(fixtanggal)) {

                TodayClassData item = new TodayClassData();

                item.setIdkegiatan(d.getIdkuliah());
                item.setFitur("Kuliah");
                item.setAcara(d.getMatakuliah());
                item.setTanggal(d.getTanggalmulai());
                item.setJammasuk(d.getJammulai());
                item.setLokasi(d.getLokasi());
                item.setRuang(d.getRuang());
                item.setOrang(d.getDosen());
                item.setPengingat(d.getPengingat());

                datatoday.add(item);

            }
        }
    }

    private void ujian() {

        Log.e("ujian", "list ujian");

//        String data[] = null;

        dataujian = db.getallujian();

        for (int i = 0; i < dataujian.size(); i++) {

            UjianClassData d = dataujian.get(i);

            Log.e("Ujian", " " + d.getUjian());

            aturtanggal(d.getTanggal());

            if (tanggalsekarang.equals(fixtanggal)) {

                TodayClassData item = new TodayClassData();

                item.setIdkegiatan(d.getId_ujian());
                item.setFitur("Ujian");
                item.setAcara(d.getUjian());
                item.setTanggal(d.getTanggal());
                item.setJammasuk(d.getJammasuk());
                item.setLokasi(d.getGedung());
                item.setRuang(d.getRuang());
                item.setOrang(d.getDosen());
                item.setPengingat(d.getPengingat());

                datatoday.add(item);

            }

        }

    }

    private void tugas() {

        Log.e("tugas", "tugas");

        String data[] = null;

        Cursor cursor = db.gettugas("SELECT * FROM tabeltugas");

//        datatoday.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tugas = cursor.getString(1);
            String keterangan = cursor.getString(2);
            String tanggal = cursor.getString(3);
            String jammasuk = cursor.getString(4);
            String gedung = cursor.getString(5);
            String dosen = cursor.getString(6);
            byte[] gambar = cursor.getBlob(7);
            String pengingat = cursor.getString(8);

            Log.e("today2", ""+tanggal);

            aturtanggal(tanggal);

            Log.e("today1", ""+tanggalsekarang);
            Log.e("today3", ""+fixtanggal);

            if (tanggalsekarang.equals(fixtanggal)) {

                TodayClassData item = new TodayClassData();

                item.setIdkegiatan(id);
                item.setFitur("Tugas");
                item.setAcara(tugas);
                item.setKegiatan(keterangan);
                item.setTanggal(fixtanggal);
                item.setJammasuk(jammasuk);
                item.setLokasi(gedung);
                item.setOrang(dosen);
                item.setPengingat(pengingat);

                datatoday.add(item);

            }

        }

    }

    private void agenda() {

        Log.e("agenda", "agenda");

        dataagenda = db.getallagenda();

        for (int i = 0; i < dataagenda.size(); i++) {

            AgendaClassData d = dataagenda.get(i);

            Log.e("agenda", "" + d.getAcara());

            aturtanggal(d.getTanggal());

            if (tanggalsekarang.equals(fixtanggal)) {

                TodayClassData item = new TodayClassData();

                item.setIdkegiatan(d.getId_agenda());
                item.setFitur("Agenda");
                item.setAcara(d.getAcara());
                item.setTanggal(d.getTanggal());
                item.setJammasuk(d.getJammasuk());
                item.setLokasi(d.getLokasi());
                item.setRuang(d.getRuang());
                item.setOrang(d.getOrang());
                item.setPengingat(d.getPengingat());

                datatoday.add(item);

            }

        }

    }

    private void aturtanggal(String tangal) {

        Calendar cal = Calendar.getInstance();
        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String tanggalbulan = tangal.substring(tangal.indexOf("-") + 1);
        final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
        String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
        String tahun = tangal.substring(0, tangal.indexOf("-"));

        Date dateawal = new Date();
        try {
            dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(tangal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(dateawal);
        int ihari = cal.get(Calendar.DAY_OF_WEEK);
        int intbulan = Integer.parseInt(bulan);


        fixtanggal = daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun;

    }
}
