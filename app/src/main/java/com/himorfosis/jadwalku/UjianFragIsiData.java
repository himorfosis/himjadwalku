package com.himorfosis.jadwalku;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UjianFragIsiData extends Fragment {

    public final static String JUDUL="JUDUL";
    public final static String ISI="ISI";
    public final static String GEDUNG ="GEDUNG";
    public final static String RUANG = "RUANG";
    public final static String ORANG = "ORANG";

    Context context;
    FloatingActionButton fab;
    Fragment fragment;
    AlertDialog alertDialog;

    EditText edmatkul, edgedung, edruang, eddosen;
    TextView tvwaktu, tvpengingat;

    Database db;

    String[] alarm = {"Tidak ada", "5 menit", "10 menit", "15 menit", "30 menit", "45 menit", "60 menit"};
    int pilihalarm;

    TimePickerDialog mTimePicker;

    Integer id;
    String acara, strtanggal, jammasuk, gedung, ruang, orang, pengingat, fixtanggal;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.ujianfragisidata, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        db = new Database(getActivity());

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.INVISIBLE);

        Button simpanujian = (Button) view.findViewById(R.id.simpan);
        edmatkul = (EditText) view.findViewById(R.id.input_matakuliah);
        tvwaktu = (TextView) view.findViewById(R.id.input_waktu);
        edgedung = (EditText) view.findViewById(R.id.input_gedung);
        edruang = (EditText) view.findViewById(R.id.input_ruang);
        eddosen = (EditText) view.findViewById(R.id.input_dosen);
        tvpengingat = (TextView) view.findViewById(R.id.input_pengingat);

        savedInstanceState = getArguments();
        if (savedInstanceState == null) {

        } else {

            id = savedInstanceState.getInt("id");
            acara = savedInstanceState.getString("acara");
            strtanggal = savedInstanceState.getString("tanggal");
            jammasuk = savedInstanceState.getString("jamawal");
            gedung = savedInstanceState.getString("gedung");
            ruang = savedInstanceState.getString("ruang");
            orang = savedInstanceState.getString("orang");
            pengingat = savedInstanceState.getString("pengingat");

        }

        if (id != null) {

            Calendar cal = Calendar.getInstance();
            String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
            String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

            String tanggalbulan = strtanggal.substring(strtanggal.indexOf("-") + 1);
            final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
            String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
            String tahun = strtanggal.substring(0, strtanggal.indexOf("-"));

            Log.d("tanggal", "-" + tanggal);

            Date dateawal = new Date();
            try {

                dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(strtanggal);

            } catch (ParseException e) {

                e.printStackTrace();

            }

            cal.setTime(dateawal);

            int ihari = cal.get(Calendar.DAY_OF_WEEK);
            int intbulan = Integer.parseInt(bulan);
            Log.d("hari awal", "-" + ihari);

            String tgl = daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun + " \n\t" + jammasuk;

            edmatkul.setText(acara);
            edgedung.setText(gedung);
            edruang.setText(ruang);
            eddosen.setText(orang);
            tvpengingat.setText(pengingat);
            tvwaktu.setText(tgl);

            Log.e("id", "" +id);
            Log.e("acara", "" +acara);
            Log.e("gedung", "" +gedung);
            Log.e("orang", "" +orang);
            Log.e("tgl", "" +strtanggal);
            Log.e("jam awal", "" +jammasuk);

        }


        simpanujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edmatkul.getText().toString().length() == 0 || tvwaktu.getText().toString().length() == 0 || edgedung.getText().toString().length() == 0 || edruang.getText().toString().length() == 0 || eddosen.getText().toString().length() == 0 || tvpengingat.getText().toString().length() == 0) {

                    Toast.makeText(getActivity(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                } else {

                    Cursor c = db.cekagenda(edmatkul.getText().toString());
                    c.moveToFirst();
                    String sjumlah = db.cekagenda0(c);
                    int jumlah = Integer.parseInt(sjumlah);

                    if (jumlah == 0) {


                        if (id != null) {

                            // update
                            db.updateujian(new UjianClassData(Integer.valueOf(id), edmatkul.getText().toString(), strtanggal, jammasuk, edgedung.getText().toString(), edruang.getText().toString(), eddosen.getText().toString(), tvpengingat.getText().toString()));
                            fragment = new UjianFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();

                            Toast.makeText(getActivity(), "Pembaruan sukses", Toast.LENGTH_SHORT).show();


                        } else {

                            //save
                            db.insertujian(new UjianClassData(null, edmatkul.getText().toString(), strtanggal, jammasuk, edgedung.getText().toString(), edruang.getText().toString(), eddosen.getText().toString(), tvpengingat.getText().toString()));
                            fragment = new UjianFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();

                            Toast.makeText(getActivity(), "Simpan sukses", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(getActivity(), "Nama acara sudah ada", Toast.LENGTH_SHORT).show();

                    }

                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    Date datebesok = new Date();
                    Date datesekarang = new Date();

                    try {

                        datebesok = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strtanggal + " " + jammasuk + ":00");

                    } catch (ParseException e) {

                        e.printStackTrace();

                    }

                    cal.setTime(datesekarang);
                    cal2.setTime(datebesok);
                    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    long diffInMs = datebesok.getTime() - datesekarang.getTime();

                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                    // set notification

                    Log.d("DETIK", "=" + diffInSec);

                    diffInSec = diffInSec - 600;
                    int diff = (int) diffInSec;

                    Intent intent = new Intent(getActivity(), TampilDialogSystemNotifikasi.class);
                    intent.putExtra(JUDUL, "Ujian");
                    intent.putExtra(ISI, edmatkul.getText().toString());
                    intent.putExtra(GEDUNG, edgedung.getText().toString());
                    intent.putExtra(RUANG, edruang.getText().toString());
                    intent.putExtra(ORANG, eddosen.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diff * 1000), pendingIntent);

                    Log.d("DETIK", "=" + diffInSec);

                    // set alarm

                    if (!"Tidak ada".equals(tvpengingat.getText().toString())) {

                        Intent intent2 = new Intent(getActivity(), TampilDialogSystemAlarm.class);
                        intent2.putExtra(JUDUL, "Ujian");
                        intent2.putExtra(ISI, edmatkul.getText().toString() + ", " + tvpengingat.getText().toString() + " lagi");
                        intent2.putExtra(GEDUNG, edgedung.getText().toString());
                        intent2.putExtra(RUANG, edruang.getText().toString());
                        intent2.putExtra(ORANG, eddosen.getText().toString());

                        long diffInSec2 = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                        int diff2 = 0;


                        if (tvpengingat.getText().equals("5 menit")) {

                            diffInSec2 = diffInSec2 - 300;
                            diff2 = (int) diffInSec2;

                        } else if (tvpengingat.getText().equals("10 menit")) {

                            diffInSec2 = diffInSec2 - 600;
                            diff2 = (int) diffInSec2;

                        } else if (tvpengingat.getText().equals("15 menit")) {

                            diffInSec2 = diffInSec2 - 900;
                            diff2 = (int) diffInSec2;

                        } else if (tvpengingat.getText().equals("30 menit")) {

                            diffInSec2 = diffInSec2 - 1800;
                            diff2 = (int) diffInSec2;


                        } else if (tvpengingat.getText().equals("45 menit")) {

                            diffInSec2 = diffInSec2 - 2700;
                            diff2 = (int) diffInSec2;


                        } else if (tvpengingat.getText().equals("60 menit")) {

                            diffInSec2 = diffInSec2 - 3600;
                            diff2 = (int) diffInSec2;

                        }

                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, intent2, 0);
                        AlarmManager alarmManager2 = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager2.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diff2 * 1000), pendingIntent2);

                    }

                    // presensi

                    int presensi = SharedPref.getIntPref("durasipresensi", "presensi", getActivity());

                    Intent cekpresensi = new Intent(getActivity(), TampilDialogSystemPresensi.class);
                    cekpresensi.putExtra("kegiatan", "Ujian");
                    cekpresensi.putExtra("isi", edmatkul.getText().toString());
                    cekpresensi.putExtra("tempat", edgedung.getText().toString());


                    long diffInSecPresensi = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                    int diffpres = 0;

                    if (presensi == 1) {

                        diffInSecPresensi = diffInSecPresensi + 900;
                        diffpres = (int) diffInSecPresensi;

                    } else if (presensi == 2) {

                        diffInSecPresensi = diffInSecPresensi + 1800;
                        diffpres = (int) diffInSecPresensi;

                    } else if (presensi == 3) {

                        diffInSecPresensi = diffInSecPresensi + 3600;
                        diffpres = (int) diffInSecPresensi;

                    } else if (presensi == 5) {

                        diffInSecPresensi = diffInSecPresensi + 7200;
                        diffpres = (int) diffInSecPresensi;

                    } else {

                        diffInSecPresensi = diffInSecPresensi + 5400;
                        diffpres = (int) diffInSecPresensi;

                    }

                    PendingIntent pendingPresensi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, cekpresensi, 0);
                    AlarmManager alarmManager3 = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager3.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diffpres * 1000), pendingPresensi);


                }
            }
        });


        tvwaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                waktu();

            }
        });

        tvpengingat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog dialog = new AlertDialog.Builder(getActivity())

                        .setTitle("Alarm sebelum kegiatan dimulai :")
                        .setSingleChoiceItems(alarm, 0, new DialogInterface.OnClickListener() {
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

                                tvpengingat.setText(alarm[pilihalarm]);

//                                SharedPref.saveIntPref("alarmnada", "alarm", selectedFont, getContext());

                            }
                        })

                        .create();
                dialog.show();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edmatkul.getText().toString().length()>0 || tvwaktu.getText().toString().length()>0 || edgedung.getText().toString().length()>0 || edruang.getText().toString().length()>0 || eddosen.getText().toString().length()>0 || tvpengingat.getText().toString().length()>0){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Peringatan");
                    builder.setMessage("Apakah anda ingin keluar ?");

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fragment = new UjianFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();
                        }
                    });

                    alertDialog = builder.create();
                    alertDialog.show();

                }
                else {
                    Log.d("NULL1", "=");
                    fragment = new UjianFragHome();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_container, fragment);
                    ft.commit();
                    Log.d("NULL2", "=");

                }
            }
        });

    }

    private void waktu() {

        final int tahun, bulan, tanggal, hari;

        Calendar c = Calendar.getInstance();
        tahun = c.get(Calendar.YEAR);
        bulan = c.get(Calendar.MONTH);
        tanggal = c.get(Calendar.DAY_OF_MONTH);
        hari = c.get(Calendar.DAY_OF_WEEK);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                        String dayOfWeek = simpledateformat.format(date);

                        Log.e("hari", "" + daysArray[hari + 1]);
                        Log.e("tanggal", "" + dayOfMonth);
                        Log.e("bulan", "" + monthArray[monthOfYear]);

                        strtanggal = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

                        fixtanggal = dayOfWeek + ", " + String.valueOf(dayOfMonth) + " " + monthArray[monthOfYear] + " " + String.valueOf(year);

                        Log.e("fix tanggal", "" +strtanggal);

                        Calendar mcurrentTime = Calendar.getInstance();
                        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        final int minute = mcurrentTime.get(Calendar.MINUTE);

                        final boolean ass = true;

                        // waktu di mulai kegiatan

                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, final int jam1, final int menit1) {
                                String sjam1 = String.valueOf(jam1);
                                String smenit1 = String.valueOf(menit1);

                                if (jam1 < 10) {

                                    sjam1 = "0" + jam1;
                                }
                                if (menit1 < 10) {
                                    smenit1 = "0" + menit1;
                                }

                                jammasuk = sjam1 + ":" + smenit1;

                                tvwaktu.setText(fixtanggal + ", jam " + jammasuk );


                            }
                        }, hour, minute, ass);

                        mTimePicker.setTitle("Jam kegiatan dimulai : ");
                        mTimePicker.show();

                    }
                }, tahun, bulan, tanggal);
        datePickerDialog.show();

    }

}
