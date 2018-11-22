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

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class KuliahFragIsiData extends Fragment {

    public final static String JUDUL = "JUDUL";
    public final static String ISI = "ISI";
    public final static String GEDUNG = "GEDUNG";
    public final static String RUANG = "RUANG";
    public final static String DOSEN = "DOSEN";

    Context context;
    FloatingActionButton fab;
    Fragment fragment;

    EditText edmatakuliah, edgedung, edruang, eddosen;
    TextView tvwaktu, tvpengingat, tvjam;

    Database db;
    AlertDialog alertDialog;
    TimePickerDialog mTimePicker;
    String tanggalmulai, tanggalselesai, fixtanggal, fixtanggal2, jam, gedung, ruang, matkul, dosen, pengingat;

    String hari, hari2;

    Integer id;

    String[] alarm = {"Tidak ada", "5 menit", "10 menit", "15 menit", "30 menit", "45 menit", "60 menit"};
    int pilihalarm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.kuliahfragisidata, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        db = new Database(getActivity());

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.INVISIBLE);

        Button simpankuliah = (Button) view.findViewById(R.id.simpan);
        edmatakuliah = (EditText) view.findViewById(R.id.input_matakuliah);
        tvwaktu = (TextView) view.findViewById(R.id.input_waktu);
        tvjam = (TextView) view.findViewById(R.id.input_jam);
        edgedung = (EditText) view.findViewById(R.id.input_gedung);
        edruang = (EditText) view.findViewById(R.id.input_ruang);
        eddosen = (EditText) view.findViewById(R.id.input_dosen);
        tvpengingat = (TextView) view.findViewById(R.id.input_pengingat);

        savedInstanceState = getArguments();
        if (savedInstanceState == null) {

        } else {

            id = savedInstanceState.getInt("id");
            matkul = savedInstanceState.getString("matkul");
            tanggalmulai = savedInstanceState.getString("tanggalmulai");
            tanggalselesai = savedInstanceState.getString("tanggalselesai");
            jam = savedInstanceState.getString("jamawal");
            gedung = savedInstanceState.getString("gedung");
            ruang = savedInstanceState.getString("ruang");
            dosen = savedInstanceState.getString("orang");
            pengingat = savedInstanceState.getString("pengingat");

        }

        if (id != null) {

            edmatakuliah.setText(matkul);
            edgedung.setText(gedung);
            edruang.setText(ruang);
            eddosen.setText(dosen);
            tvpengingat.setText(pengingat);
            tvjam.setText(jam);
            tvwaktu.setText(tanggalmulai + " - " + tanggalselesai);

            Log.e("id", "" + id);
            Log.e("acara", "" + matkul);
            Log.e("gedung", "" + gedung);
            Log.e("orang", "" + dosen);
            Log.e("tgl", "" + tanggalmulai);
            Log.e("jam awal", "" + jam);

        }


        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edmatakuliah.getText().toString().length() > 0 || tvwaktu.getText().toString().length() > 0 || edgedung.getText().toString().length() > 0 || edruang.getText().toString().length() > 0 || eddosen.getText().toString().length() > 0 || tvpengingat.getText().toString().length() > 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Peringatan");

                    builder.setMessage("Apakah anda ingin kembali");

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }

                    });

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fragment = new KuliahFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();
                        }
                    });

                    alertDialog = builder.create();
                    alertDialog.show();

                } else {

                    Log.d("NULL1", "=");
                    fragment = new KuliahFragHome();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_container, fragment);
                    ft.commit();
                    Log.d("NULL2", "=");

                }
            }
        });


        simpankuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edmatakuliah.getText().toString().length() == 0 || tvwaktu.getText().toString().length() == 0 || edgedung.getText().toString().length() == 0 || edruang.getText().toString().length() == 0 || eddosen.getText().toString().length() == 0 || tvpengingat.getText().toString().length() == 0) {

                    Toast.makeText(getActivity(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                } else {

                    Cursor c = db.cekmatkul(edmatakuliah.getText().toString());
                    c.moveToFirst();
                    String sjumlah = db.cekmatkul0(c);

                    int jumlah = Integer.parseInt(sjumlah);

                    if (jumlah == 0) {

                        if (id != null) {

                            //update

                            db.updatekuliah(new KuliahClassData(id, edmatakuliah.getText().toString(), tanggalmulai, tanggalselesai, jam, edgedung.getText().toString(), edruang.getText().toString(), eddosen.getText().toString(), tvpengingat.getText().toString()));
                            fragment = new KuliahFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();

                            Toast.makeText(getActivity(), "Pembaruan sukses", Toast.LENGTH_SHORT).show();

                        } else {

                            //save

                            db.insertjadwalkuliah(new KuliahClassData(null, edmatakuliah.getText().toString(), tanggalmulai, tanggalselesai, jam, edgedung.getText().toString(), edruang.getText().toString(), eddosen.getText().toString(), tvpengingat.getText().toString()));
                            fragment = new KuliahFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();

                            Toast.makeText(getActivity(), "Simpan sukses", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(getActivity(), "Mata kuliah sudah ada", Toast.LENGTH_SHORT).show();

                    }


                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    Date datebesok = new Date();
                    Date datesekarang = new Date();

                    int hari = cal.get(Calendar.DAY_OF_WEEK);

                    try {

                        datebesok = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tanggalmulai + " " + jam + ":00");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    cal.setTime(datesekarang);
                    cal2.setTime(datebesok);
                    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    long diffInMs = datebesok.getTime() - datesekarang.getTime();

                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                    Log.d("DETIK", "=" + diffInSec);
                    diffInSec = diffInSec - 600;
                    int diff = (int) diffInSec;
                    Log.d("DETIK", "=" + diffInSec);

                    Intent intent = new Intent(getActivity(), TampilDialogSystemNotifikasi.class);
                    intent.putExtra(JUDUL, "Kuliah");
                    intent.putExtra(ISI, edmatakuliah.getText().toString());
                    intent.putExtra(GEDUNG, edgedung.getText().toString());
                    intent.putExtra(RUANG, edruang.getText().toString());
                    intent.putExtra(DOSEN, eddosen.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diff * 1000), pendingIntent);

                    Log.e("DATA DI SIMPAN", " ");
                    Log.e("Tanggal Awal", "= " + fixtanggal);
                    Log.e("Tanggal Akhir", "= " + fixtanggal2);
                    Log.e("tanggal sekarang", "= " + datesekarang);
                    Log.e("tanggal mulai", "= " + datebesok);
                    Log.e("hari ", "= " + hari);
                    Log.e("cal get ", "= " + cal.get(Calendar.DAY_OF_WEEK));
                    Log.e("jam  ", "= " + jam);
                    Log.e("sekarang get time", "= " + datesekarang.getTime());
                    Log.e("mulai get time", "= " + datebesok.getTime());

                    long diffInSec2 = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                    int diff2 = 0;

                    Intent intent2 = new Intent(getActivity(), TampilDialogSystemPresensi.class);
                    intent2.putExtra(JUDUL, "Kuliah");
                    intent2.putExtra(ISI, edmatakuliah.getText().toString() + ", " + tvpengingat.getText().toString() + " lagi");
                    intent2.putExtra(GEDUNG, edgedung.getText().toString());
                    intent2.putExtra(RUANG, edruang.getText().toString());
                    intent2.putExtra(DOSEN, eddosen.getText().toString());

                    if (!"Tidak ada".equals(tvpengingat.getText().toString())) {


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
                    cekpresensi.putExtra("kegiatan", "Kuliah");
                    cekpresensi.putExtra("isi", edmatakuliah.getText().toString());
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.kuliahwaktu, null);

                Button pilihwaktu = dialogView.findViewById(R.id.pilihwaktu);
//                final TextView jamkuliah =  dialogView.findViewById(R.id.jam);
                final TextView tglmulai =  dialogView.findViewById(R.id.tglmulai);
                final TextView tglselesai =  dialogView.findViewById(R.id.tglselesai);

                final int tahun, bulan, tanggal;



                Calendar c = Calendar.getInstance();
                tahun = c.get(Calendar.YEAR);
                bulan = c.get(Calendar.MONTH);
                tanggal = c.get(Calendar.DAY_OF_MONTH);
//                hari = c.get(Calendar.DAY_OF_WEEK);
//                hari2 = c.get(Calendar.DAY_OF_WEEK);

                tglmulai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                                        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                                        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                                        String dayOfWeek = simpledateformat.format(date);

                                        Log.e("tanggal", "" + dayOfMonth);
                                        Log.e("bulan", "" + monthArray[monthOfYear]);
                                        Log.e("year", "" + year);
                                        Log.e("dayOfMonth", "" + dayOfMonth);

                                        tanggalmulai = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

                                        fixtanggal = dayOfWeek + ", " + String.valueOf(dayOfMonth) + " " + monthArray[monthOfYear] + " " + String.valueOf(year);

                                        tglmulai.setText(fixtanggal);

                                        hari = dayOfWeek;

                                    }

                                }, tahun, bulan, tanggal);
                        datePickerDialog.show();

                    }
                });

                tglselesai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                                        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                                        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                                        String dayOfWeek = simpledateformat.format(date);

                                        Log.e("tanggal", "" + dayOfMonth);
                                        Log.e("bulan", "" + monthArray[monthOfYear]);
                                        Log.e("year", "" + year);
                                        Log.e("dayOfMonth", "" + dayOfMonth);

                                        tanggalselesai = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

                                        fixtanggal2 = dayOfWeek + ", " + String.valueOf(dayOfMonth) + " " + monthArray[monthOfYear] + " " + String.valueOf(year);

                                        tglselesai.setText(fixtanggal2);

                                        hari2 = dayOfWeek;

                                    }

                                }, tahun, bulan, tanggal);
                        datePickerDialog.show();

                    }
                });


                pilihwaktu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("hari1", "" + hari);
                        Log.e("hari2", "" +hari2);

                        if (tglmulai.getText().toString().length() == 0 || tglselesai.getText().toString().length() == 0) {

                            Toast.makeText(getActivity(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                        } else if (!hari.equals(hari2)) {

                            Toast.makeText(getActivity(), "Mohon sesuaikan hari", Toast.LENGTH_SHORT).show();

                        } else {

                            tvwaktu.setText(fixtanggal + " - " + fixtanggal2);
                            alertDialog.dismiss();

                        }

                    }
                });


                builder.setView(dialogView);

                alertDialog = builder.create();
                alertDialog.show();

            }

        });

        tvjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                        jam = sjam1 + ":" + smenit1;

                        tvjam.setText(jam);


                    }
                }, hour, minute, ass);

                mTimePicker.setTitle("Jam kegiatan dimulai : ");
                mTimePicker.show();

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

    }

}
