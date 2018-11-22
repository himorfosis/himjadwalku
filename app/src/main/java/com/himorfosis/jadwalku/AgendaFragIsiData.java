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

import static java.security.AccessController.getContext;

public class AgendaFragIsiData extends Fragment {

    public final static String JUDUL = "JUDUL";
    public final static String ISI = "ISI";
    public final static String GEDUNG = "GEDUNG";
    public final static String RUANG = "RUANG";
    public final static String ORANG = "ORANG";

    Fragment fragment;

    EditText edacara, edgedung, edruang, edorang;
    TextView tvwaktu, tvpengingat;

    Database db;
    AlertDialog alertDialog;
    TimePickerDialog mTimePicker;

    CaldroidListener listener;

    String[] alarm = {"Tidak ada", "5 menit", "10 menit", "15 menit", "30 menit", "45 menit", "60 menit"};
    int pilihalarm;

    String fixtanggal;

    Integer id;
    String acara, strtanggal, jamawal, gedung, ruang, orang, pengingat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.agendafragisidata, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();
        savedInstanceState = getArguments();

        db = new Database(getActivity());

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final Button back = (Button) actionBar.getCustomView().findViewById(R.id.kembali);
        back.setVisibility(View.VISIBLE);

        LinearLayout edit = (LinearLayout) actionBar.getCustomView().findViewById(R.id.btnedit);
        edit.setVisibility(View.INVISIBLE);

        edacara = (EditText) view.findViewById(R.id.input_acara);
        tvwaktu = (TextView) view.findViewById(R.id.input_waktu);
        edgedung = (EditText) view.findViewById(R.id.input_gedung);
        edruang = (EditText) view.findViewById(R.id.input_ruang);
        edorang = (EditText) view.findViewById(R.id.input_orang);
        tvpengingat = (TextView) view.findViewById(R.id.input_pengingat);
        Button simpanagenda = (Button) view.findViewById(R.id.simpan);


        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        savedInstanceState = getArguments();
        if (savedInstanceState == null) {

        } else {

            id = savedInstanceState.getInt("id");
            acara = savedInstanceState.getString("acara");
            strtanggal = savedInstanceState.getString("tanggal");
            jamawal = savedInstanceState.getString("jamawal");
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

            String tgl = daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun + " \n\t" + jamawal;

            edacara.setText(acara);
            edgedung.setText(gedung);
            edruang.setText(ruang);
            edorang.setText(orang);
            tvpengingat.setText(pengingat);
            tvwaktu.setText(tgl);

            Log.e("id", "" + id);
            Log.e("acara", "" + acara);
            Log.e("gedung", "" + gedung);
            Log.e("orang", "" + orang);
            Log.e("tgl", "" + strtanggal);
            Log.e("jam awal", "" + jamawal);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edacara.getText().toString().length() > 0 || tvwaktu.getText().toString().length() > 0 || edgedung.getText().toString().length() > 0 || edruang.getText().toString().length() > 0 || edorang.getText().toString().length() > 0 || tvpengingat.getText().toString().length() > 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Peringatan");
                    builder.setMessage("Apakah anda ingin kembali ?");


                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fragment = new AgendaFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();
                        }
                    });

                    alertDialog = builder.create();
                    alertDialog.show();

                } else {
                    Log.d("NULL1", "=");
                    fragment = new AgendaFragHome();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_container, fragment);
                    ft.commit();
                    Log.d("NULL2", "=");

                }
            }
        });


        simpanagenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("edacara", "" + edacara.getText().toString());
                Log.e("edwaktu", "" + tvwaktu.getText().toString());
                Log.e("edgedung", "" + edgedung.getText().toString());
                Log.e("edruang", "" + edruang.getText().toString());
                Log.e("edorang", "" + edorang.getText().toString());


                if (edacara.getText().toString().equals("") || tvwaktu.getText().toString().equals("") || edgedung.getText().toString().equals("") || edruang.getText().toString().equals("") || edorang.getText().toString().equals("") || tvpengingat.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                } else {

                    Cursor c = db.cekagenda(edacara.getText().toString());
                        c.moveToFirst();
                        String sjumlah = db.cekagenda0(c);
                        int jumlah = Integer.parseInt(sjumlah);

                        if (jumlah == 0) {

                        if (id != null) {

                            // update

                            db.updateagenda(new AgendaClassData(Integer.valueOf(id), edacara.getText().toString(), strtanggal, jamawal, edgedung.getText().toString(), edruang.getText().toString(), edorang.getText().toString(), tvpengingat.getText().toString()));
                            fragment = new AgendaFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();

                        } else {

                            //save

                            db.insertagenda(new AgendaClassData(null, edacara.getText().toString(), strtanggal, jamawal, edgedung.getText().toString(), edruang.getText().toString(), edorang.getText().toString(), tvpengingat.getText().toString()));
                            fragment = new AgendaFragHome();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_container, fragment);
                            ft.commit();

                        }

                    } else {

                        Toast.makeText(getActivity(), "Nama acara sudah ada", Toast.LENGTH_SHORT).show();

                    }

                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();

                    Date datebesok = new Date();
                    Date datesekarang = new Date();

                    Log.e("date", "" + strtanggal);
                    Log.e("jam", "" + jamawal);

                    try {
                        datebesok = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strtanggal + " " + jamawal + ":00");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.e("date sekarang", "" + datesekarang);
                    Log.e("date besok", "" + datebesok);

                    cal.setTime(datesekarang);
                    cal2.setTime(datebesok);

                    Log.e("sekarang", "" + datesekarang.getTime());
                    Log.e("besok", "" + datebesok.getTime());

                    // set notification

                    long diffInMs = datebesok.getTime() - datesekarang.getTime();

                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                    diffInSec = diffInSec - 600;
                    int notif = (int) diffInSec;

                    Log.e("diffinMs", "" + diffInMs);
                    Log.e("diffInSec", "" + diffInSec);
                    Log.e("diff", "" + notif);

                    Log.e("besok", "" + datebesok.getTime());

                    Log.e("Tanggal sekarang ", "= " + datesekarang);
                    Log.e("Tanggal besok ", "= " + datebesok);
                    Log.e("Jam masuk ", "= " + jamawal);
                    Log.e("formatedate1  ", "= " + formatter);
                    Log.e("DETIK", "=" + diffInSec);

                    Intent intent = new Intent(getActivity(), TampilDialogSystemNotifikasi.class);
                    intent.putExtra(JUDUL, "Agenda");
                    intent.putExtra(GEDUNG, edgedung.getText().toString());
                    intent.putExtra(RUANG, edruang.getText().toString());
                    intent.putExtra(ISI, edacara.getText().toString());
                    intent.putExtra(ORANG, edorang.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, intent, 0);
                    AlarmManager notifmanager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    notifmanager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (notif * 1000), pendingIntent);

                    Log.e("diff", "" + notif);

//                    long notifSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
//                    notifSec = notifSec - 1800;
//                    int alarmtime = (int) notifSec;

//                    Log.e("diff alarm", "= " + alarmtime);
//                    Log.e("diff notif", "= " + notif);
//                    Log.e("diff notif sec", "= " + notifSec);


                    if (!"Tidak ada".equals(tvpengingat.getText().toString())) {

                        Intent alarm = new Intent(getActivity(), TampilDialogSystemAlarm.class);
                        alarm.putExtra(JUDUL, "Agenda");
                        alarm.putExtra(GEDUNG, edgedung.getText().toString());
                        alarm.putExtra(RUANG, edruang.getText().toString());
                        alarm.putExtra(ISI, edacara.getText().toString() + ", " + tvpengingat.getText().toString() + " lagi");
                        alarm.putExtra(ORANG, edorang.getText().toString());

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

                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, alarm, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diff2 * 1000), pendingIntent2);

                        Log.e("Alarm", " Aktif");

                    }

                    // presensi

                    int presensi = SharedPref.getIntPref("durasipresensi", "presensi", getActivity());

                    Log.e("durasi presensi", "" +presensi);

                    Intent cekpresensi = new Intent(getActivity(), TampilDialogSystemPresensi.class);
                    cekpresensi.putExtra("kegiatan", "Agenda");
                    cekpresensi.putExtra("isi", edacara.getText().toString());
                    cekpresensi.putExtra("tempat", edgedung.getText().toString());


                    long diffInSecPresensi = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                    int diffpres = 0;

                    Log.e("diff presensi", ""+diffInSecPresensi);

                    if (presensi == 0) {

                        Log.e("presensi aktif", "aktif 0");

                        diffInSecPresensi = diffInSecPresensi + 60;
                        diffpres = (int) diffInSecPresensi;

                    } else if (presensi == 1) {

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

                        Log.e("presensi aktif", " 60");

                        diffInSecPresensi = diffInSecPresensi + 60;
                        diffpres = (int) diffInSecPresensi;

                    }

                    Log.e("pres aktif", "" +diffInSecPresensi);
                    Log.e("diffpress", "" +diffpres);

                    PendingIntent pendingPresensi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, cekpresensi, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diffpres * 1000), pendingPresensi);

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

    }

    private void waktu() {

        final int tahun, bulan, tanggal, hari;

        Calendar c = Calendar.getInstance();
        tahun = c.get(Calendar.YEAR);
        bulan = c.get(Calendar.MONTH);
        tanggal = c.get(Calendar.DAY_OF_MONTH);
//        hari = c.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

                        Log.e("tanggal", "" + dayOfMonth);
                        Log.e("bulan", "" + monthArray[monthOfYear]);
                        Log.e("year", "" + year);
                        Log.e("dayOfMonth", "" + dayOfMonth);

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                        String dayOfWeek = simpledateformat.format(date);

                        Log.e("day", "" + dayOfWeek);
//                        Log.e("month", "" + month);

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

                                jamawal = sjam1 + ":" + smenit1;

                                tvwaktu.setText(fixtanggal + ", Jam : " + jamawal);

                            }
                        }, hour, minute, ass);

                        mTimePicker.setTitle("Jam kegiatan dimulai : ");
                        mTimePicker.show();

                    }
                }, tahun, bulan, tanggal);
        datePickerDialog.show();

    }

}
