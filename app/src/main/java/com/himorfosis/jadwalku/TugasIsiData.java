package com.himorfosis.jadwalku;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.CAMERA;

public class TugasIsiData extends AppCompatActivity {

    public final static String JUDUL="JUDUL";
    public final static String ISI="ISI";
    public final static String GEDUNG ="GEDUNG";
    public final static String RUANG = "RUANG";
    public final static String ORANG = "ORANG";

    String jammasuk, formattedDate, fixtanggal;
    EditText deskripsitugas, edmatkul, edgedung, eddosen;
    TextView tvwaktu, tvpengingat;
    LinearLayout galeri, kamera;
    ImageView gambar;

    TimePickerDialog mTimePicker, mTimePicker2;
    private CaldroidFragment dialogCaldroidFragment;
    AlertDialog alertDialog;

    //kamera and galeri
    private int GALLERY = 1, TAKECAMERA = 2;
    byte[] byteGambar;

    ByteArrayOutputStream baos;
    Drawable drawable;

    String[] alarm = {"Tidak ada", "5 menit", "10 menit", "15 menit", "30 menit", "45 menit", "60 menit"};
    int pilihalarm;

    Integer getid;

    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugasfragisidata);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Tugas");

        // database

        db = new Database(getApplicationContext());

        edmatkul = (EditText) findViewById(R.id.matakuliah);
        deskripsitugas = (EditText) findViewById(R.id.deskriptugas);
        tvwaktu = (TextView) findViewById(R.id.input_waktu);
        edgedung = (EditText) findViewById(R.id.input_gedung);
        eddosen = (EditText) findViewById(R.id.input_dosen);
        tvpengingat = (TextView) findViewById(R.id.input_pengingat);
        galeri = findViewById(R.id.galeri);
        kamera = findViewById(R.id.kamera);
        gambar = findViewById(R.id.gambar);
        Button simpankuliah = (Button) findViewById(R.id.simpan);

        Intent bundle = getIntent();

        String id = bundle.getStringExtra("id");

        Log.e("id", ""+id);

        if (id.equals("kosong")) {

            Log.e("id", ""+id);

        } else {

            getid = Integer.valueOf(id);

            Cursor cursor = db.gettugas("SELECT * FROM tabeltugas");

            while (cursor.moveToNext()) {
                int idtugas = cursor.getInt(0);
                String dbtugas = cursor.getString(1);
                String dbketerangan = cursor.getString(2);
                String dbtanggal = cursor.getString(3);
                String dbjammasuk = cursor.getString(4);
                String dbgedung = cursor.getString(5);
                String dbdosen = cursor.getString(6);
                byte[] dbgambar = cursor.getBlob(7);
                String dbpengingat = cursor.getString(8);

                if (getid == idtugas) {

                    formattedDate = dbtanggal;
                    jammasuk = dbjammasuk;

                    Calendar cal = Calendar.getInstance();
                    String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                    String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

                    String tanggalbulan = formattedDate.substring(formattedDate.indexOf("-") + 1);
                    final String tanggal = tanggalbulan.substring(tanggalbulan.lastIndexOf("-") + 1);
                    String bulan = tanggalbulan.substring(0, tanggalbulan.indexOf("-"));
                    String tahun = formattedDate.substring(0, formattedDate.indexOf("-"));

                    Log.d("tanggal", "-" + tanggal);

                    Date dateawal = new Date();
                    try {

                        dateawal = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);

                    } catch (ParseException e) {

                        e.printStackTrace();

                    }

                    cal.setTime(dateawal);

                    int ihari = cal.get(Calendar.DAY_OF_WEEK);
                    int intbulan = Integer.parseInt(bulan);

                    String tgl = daysArray[ihari] + ", " + tanggal + " " + monthArray[intbulan - 1] + " " + tahun + " \n\t" + jammasuk;

                    edmatkul.setText(dbtugas);
                    tvwaktu.setText(tgl);
                    deskripsitugas.setText(dbketerangan);
                    edgedung.setText(dbgedung);
                    eddosen.setText(dbdosen);
                    tvpengingat.setText(dbpengingat);

                    byteGambar = dbgambar;

                    gambar.setImageBitmap(BitmapFactory.decodeByteArray( dbgambar, 0, dbgambar.length));


                }

            }
        }


        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);

            }
        });

        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkPermission()) {

                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, TAKECAMERA);


                    } else {

//                        requestPermission();
                        requestPermissions(  new String[]{CAMERA}, TAKECAMERA);

                    }

                } else {

                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKECAMERA);
                }

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(TugasIsiData.this, Tugas.class);
                startActivity(in);

            }
        });

        simpankuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(deskripsitugas.getText().toString().length() == 0 || edmatkul.getText().toString().length() == 0 || tvwaktu.getText().toString().length()==0 || edgedung.getText().toString().length()==0 ||  eddosen.getText().toString().length()==0 || tvpengingat.getText().toString().length()==0){

                    Toast.makeText(getApplicationContext(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                } else {

                    if (byteGambar == null) {


                        // save

                        drawable = getResources().getDrawable(R.drawable.noimage);

                        try {

                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                            baos = new ByteArrayOutputStream();

                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        } finally {
                            if (baos != null) {
                                try {

                                    Log.e("conv gambar", "" + baos);

                                    byteGambar = baos.toByteArray();

                                    Log.e("byteGambar", "" + baos);

                                    baos.close();

                                } catch (IOException e) {

                                    Log.e("conv gambar", "ByteArrayOutputStream was not closed");

                                }
                            }
                        }

                    }

                    Cursor c = db.cektugas(edmatkul.getText().toString());
                    c.moveToFirst();
                    String sjumlah = db.cektugas0(c);
                    int jumlah = Integer.parseInt(sjumlah);

                    if (jumlah == 0) {

                        if (getid != null) {

                            // update

                            db.updatetugas(deskripsitugas.getText().toString(), edmatkul.getText().toString(), formattedDate, jammasuk, edgedung.getText().toString(), eddosen.getText().toString(), byteGambar, tvpengingat.getText().toString(), getid);

                            Toast.makeText(getApplicationContext(), "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show();


                        } else {

                            db.inserttugas(deskripsitugas.getText().toString(), edmatkul.getText().toString(), formattedDate, jammasuk, edgedung.getText().toString(), eddosen.getText().toString(), byteGambar, tvpengingat.getText().toString());

                            Toast.makeText(getApplicationContext(), "Tugas berhasil disimpan", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(TugasIsiData.this, "Matakuliah sudah ada", Toast.LENGTH_SHORT).show();

                    }

                    Intent in = new Intent(TugasIsiData.this, Tugas.class);
                    startActivity(in);

                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    Date datebesok = new Date();
                    Date datesekarang = new Date();
                    try {
                        datebesok = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formattedDate+" "+jammasuk+":00");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.setTime(datesekarang);
                    cal2.setTime(datebesok);
                    //  final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    long diffInMs = datebesok.getTime() - datesekarang.getTime();

                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);


                    diffInSec = diffInSec - 600;
                    int diff = (int) diffInSec;
                    Log.d("Diff in sec", "="+diffInSec);
                    Log.d("tanggal sekarang", "="+datesekarang);

                    Intent intent = new Intent(TugasIsiData.this, TampilDialogSystemNotifikasi.class);
                    intent.putExtra(JUDUL, "Tugas");
                    intent.putExtra(ISI, edmatkul.getText().toString());
                    intent.putExtra(GEDUNG, edgedung.getText().toString());
                    intent.putExtra(ORANG, eddosen.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(TugasIsiData.this, 234324243, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                            + ( diff * 1000), pendingIntent);

                    if(!"Tidak ada".equals(tvpengingat.getText().toString())) {


                        Intent intent2 = new Intent(TugasIsiData.this, TampilDialogSystemAlarm.class);
                        intent2.putExtra(JUDUL, "Tugas");
                        intent2.putExtra(ISI, edmatkul.getText().toString() + ", " + tvpengingat.getText().toString() + " lagi");
                        intent2.putExtra(GEDUNG, edgedung.getText().toString());
                        intent2.putExtra(ORANG, eddosen.getText().toString());


                        long diffInSec2 = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                        int diff2 = (int) diffInSec2;


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

                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(TugasIsiData.this, 234324243, intent2, 0);
                        AlarmManager alarmManager2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager2.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (diff2 * 1000), pendingIntent2);

                    }

                    // presensi

                    int presensi = SharedPref.getIntPref("durasipresensi", "presensi", getApplicationContext());

                    Intent cekpresensi = new Intent(TugasIsiData.this, TampilDialogSystemPresensi.class);
                    cekpresensi.putExtra("kegiatan", "Tugas");
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

                    PendingIntent pendingPresensi = PendingIntent.getBroadcast(getApplicationContext().getApplicationContext(), 234324243, cekpresensi, 0);
                    AlarmManager alarmManager3 = (AlarmManager) getApplicationContext().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
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

                AlertDialog dialog = new AlertDialog.Builder(TugasIsiData.this)

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
        hari = c.get(Calendar.DAY_OF_WEEK);


        DatePickerDialog datePickerDialog = new DatePickerDialog(TugasIsiData.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                        String[] daysArray = new String[]{"", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                        String[] monthArray = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                        String dayOfWeek = simpledateformat.format(date);

                        Log.e("hari", "" + dayOfWeek);
                        Log.e("tanggal", "" + dayOfMonth);
                        Log.e("bulan", "" + monthArray[monthOfYear]);

                        formattedDate = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

                        fixtanggal = dayOfWeek + ", " + String.valueOf(dayOfMonth) + " " + monthArray[monthOfYear] + " " + String.valueOf(year);

                        Log.e("fix tanggal", "" +formattedDate);


                        Calendar mcurrentTime = Calendar.getInstance();
                        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        final int minute = mcurrentTime.get(Calendar.MINUTE);

                        final boolean ass = true;

                        // waktu di mulai kegiatan

                        mTimePicker = new TimePickerDialog(TugasIsiData.this, new TimePickerDialog.OnTimeSetListener() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    gambar.setImageBitmap(bitmap);

                    baos = null;

                    try {

                        baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    } finally {

                        if (baos != null) {
                            try {

                                Log.e("conv gambar", "" +baos);

                                byteGambar = baos.toByteArray();

                                Log.e("byteGambar", "" +baos);

                                baos.close();

                            } catch (IOException e) {

                                Log.e("conv gambar", "ByteArrayOutputStream was not closed");

                            }
                        }
                    }


                    Log.e("byteGambar", "" +byteGambar);

                } catch (IOException e) {

                    e.printStackTrace();
                    Toast.makeText(TugasIsiData.this, "Gagal!", Toast.LENGTH_SHORT).show();

                }
            }

        } else if (requestCode == TAKECAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            gambar.setImageBitmap(thumbnail);

            baos = null;
            try {

                baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);


            } finally {
                if (baos != null) {
                    try {

                        Log.e("conv gambar", "" +baos);

                        byteGambar = baos.toByteArray();

                        Log.e("byteGambar", "" +baos);

                        baos.close();

                    } catch (IOException e) {

                        Log.e("conv gambar", "ByteArrayOutputStream was not closed");

                    }
                }
            }
        }
    }

    private boolean checkPermission() {

        return (PermissionChecker.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

//    private void requestPermission() {
//
//        requestPermissions(  new String[]{CAMERA}, TAKECAMERA);
//
//    }

}
