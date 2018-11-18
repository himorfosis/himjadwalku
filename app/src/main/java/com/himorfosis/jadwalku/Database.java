package com.himorfosis.jadwalku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DatabaseName = "jadwalkuapp";
    private static final int DatabaseVersion = 1;


    public Database(Context context) { super (context, DatabaseName, null, DatabaseVersion);

        //Untuk auto generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //untuk auto generated method stub

        db.execSQL("CREATE TABLE tabeljadwalkuliah (id_jadwalkuliah INTEGER PRIMARY KEY AUTOINCREMENT, matakuliah TEXT NOT NULL, tanggalawal TEXT NOT NULL, tanggalselesai TEXT NOT NULL, jammasuk TEXT NOT NULL, gedung TEXT NOT NULL, ruang TEXT NOT NULL, dosen TEXT NOT NULL, pengingat TEXT NOT NULL);");
        db.execSQL("CREATE TABLE tabelcatatan (id_catatan INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT NOT NULL, isi TEXT NOT NULL);");
        db.execSQL("CREATE TABLE tabelagenda (id_agenda INTEGER PRIMARY KEY AUTOINCREMENT, acara TEXT NOT NULL, tanggal TEXT NOT NULL, jammasuk TEXT NOT NULL, lokasi TEXT NOT NULL, ruang TEXT NOT NULL, orang TEXT NOT NULL, pengingat TEXT NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS tabeltugas (Id INTEGER PRIMARY KEY AUTOINCREMENT, tugas VARCHAR, keterangan VARCHAR, tanggal VARCHAR, jammasuk VARCHAR, gedung VARCHAR, dosen VARCHAR, gambar BLOB, pengingat VARCHAR);");
        db.execSQL("CREATE TABLE tabelnotifikasi  (id_notifikasi INTEGER PRIMARY KEY AUTOINCREMENT, id_music TEXT NOT NULL, menu TEXT NOT NULL);");
        db.execSQL("CREATE TABLE tabelujian (id_ujian INTEGER PRIMARY KEY AUTOINCREMENT, ujian TEXT NOT NULL, tanggal TEXT NOT NULL, jammasuk TEXT NOT NULL, gedung TEXT NOT NULL, ruang TEXT NOT NULL, dosen TEXT NOT NULL, pengingat TEXT NOT NULL);");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS tabelcatatan");
        db.execSQL("DROP TABLE IF EXISTS tabelagenda");
        db.execSQL("DROP TABLE IF EXISTS tabeljadwalkuliah");
        db.execSQL("DROP TABLE IF EXISTS tabelmenusuara");
        db.execSQL("DROP TABLE IF EXISTS tabelnotifikasi");
        db.execSQL("DROP TABLE IF EXISTS tabeltugas");
        db.execSQL("DROP TABLE IF EXISTS tabelujian");

        onCreate(db);
    }


    /**
     * ===================== KULIAH ===========================
     */

    public void insertjadwalkuliah(KuliahClassData classdata) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id_jadwalkuliah", classdata.getIdkuliah());
        cv.put("matakuliah", classdata.getMatakuliah());
        cv.put("tanggalawal", classdata.getTanggalmulai());
        cv.put("tanggalselesai", classdata.getTanggalselesai());
        cv.put("gedung", classdata.getLokasi());
        cv.put("jammasuk", classdata.getJammulai());
        cv.put("ruang", classdata.getRuang());
        cv.put("dosen", classdata.getDosen());
        cv.put("pengingat", classdata.getPengingat());

        db.insert("tabeljadwalkuliah", null, cv);
        db.close();
    }

    public List<KuliahClassData> getallkuliah() {
        List<KuliahClassData> dataArray = new ArrayList<KuliahClassData>();
        String query = "SELECT * FROM tabeljadwalkuliah" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null );
        if (cursor.moveToFirst()) {
            do {
                KuliahClassData datalist = new KuliahClassData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                dataArray.add(datalist);

            } while (cursor.moveToNext());
        }
        return dataArray;
    }

    public void updatekuliah(KuliahClassData classdata) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("matakuliah", classdata.getMatakuliah());
        cv.put("tanggalawal", classdata.getTanggalmulai());
        cv.put("tanggalselesai", classdata.getTanggalselesai());
        cv.put("gedung", classdata.getLokasi());
        cv.put("jammasuk", classdata.getJammulai());
        cv.put("ruang", classdata.getRuang());
        cv.put("dosen", classdata.getDosen());
        cv.put("pengingat", classdata.getPengingat());

        db.update("tabeljadwalkuliah", cv, "id_jadwalkuliah = ?", new String[]{String.valueOf(classdata.getIdkuliah())});
        db.close();

    }

    public void deletejadwalkuliah(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={id};
        ContentValues cv = new ContentValues();

        db.delete("tabeljadwalkuliah", "id_jadwalkuliah=?", args);
        db.close();

    }

    public Cursor cekmatkul(String status){
        String[] args={status};
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(matakuliah) FROM tabeljadwalkuliah WHERE matakuliah=?", args));
    }

    public String cekmatkul0(Cursor c){
        return(c.getString(0));
    }

    /**
     * ===================== TUGAS ===========================
     */


    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void inserttugas( String keterangan, String matkul, String tanggal, String jammasuk, String gedung, String dosen, byte[] gambar, String pengingat) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO tabeltugas VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, keterangan);
        statement.bindString(2, matkul);
        statement.bindString(3, tanggal);
        statement.bindString(4, jammasuk);
        statement.bindString(5, gedung);
        statement.bindString(6, dosen);
        statement.bindBlob(7, gambar);
        statement.bindString(8, pengingat);

        statement.executeInsert();

    }

    public  void deletetugas(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM tabeltugas WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public void updatetugas(String tugas, String keterangan, String tanggal, String jammasuk, String gedung, String dosen, byte[] gambar, String pengingat, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE tabeltugas SET tugas = ?, keterangan = ?, tanggal = ?,  jammasuk = ?, gedung = ?, dosen = ?, gambar = ?, pengingat = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, tugas);
        statement.bindString(2, keterangan);
        statement.bindString(3, tanggal);
        statement.bindString(4, jammasuk);
        statement.bindString(5, gedung);
        statement.bindString(6, dosen);
        statement.bindBlob(7, gambar);
        statement.bindString(8, pengingat);
        statement.bindDouble(9, (double)id);

        statement.execute();
        database.close();

    }

    public Cursor gettugas(String sql){

        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }

    public Cursor cektugas(String status){
        String[] args={status};
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(tugas) FROM tabeltugas WHERE tugas=?", args));
    }

    public String cektugas0(Cursor c){
        return(c.getString(0));
    }


    public Cursor getcounttugas(String tanggalawal, String tanggalakhir){
        String arg=tanggalawal;
        String[] args={tanggalakhir};
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(tanggal) FROM tabeltugas WHERE tanggal>='"+arg+"' AND tanggal<=?", args));
    }
    public String getcounttugas0(Cursor c){
        return(c.getString(0));
    }


    /**
     * ===================== CATATAN ===========================
     */

    public void insertcatatan(CatatanClassData classdata) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();

        cv.put("id_catatan", classdata.getId_catatan());
        cv.put("judul", classdata.getJudul());
        cv.put("isi", classdata.getIsi());


        db.insert("tabelcatatan", null, cv);
        db.close();
    }

    public List<CatatanClassData> getallcatatan() {
        List<CatatanClassData> dataArray = new ArrayList<CatatanClassData>();
        String query = "SELECT * FROM tabelcatatan ORDER BY id_catatan DESC" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null );
        if (cursor.moveToFirst()) {
            do {
                CatatanClassData datalist = new CatatanClassData(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                dataArray.add(datalist);
            } while (cursor.moveToNext());
        }
        return dataArray;
    }

    public void updatetabelcatatan(String id, String judul, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={id};
        ContentValues cv = new ContentValues();
        cv.put("judul", judul);
        cv.put("isi", isi);

        db.update("tabelcatatan", cv, "id_catatan=?", args);
        db.close();

    }

    public void deletecatatan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={id};
        ContentValues cv = new ContentValues();

        db.delete("tabelcatatan", "id_catatan=?", args);
        db.close();

    }

    public Cursor cekcatatan(String status){
        String[] args={status};
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(judul) FROM tabelcatatan WHERE judul=?", args));
    }

    public String cekcatatan0(Cursor c){
        return(c.getString(0));
    }


       /* public Cursor getcounttabelcatatan(){
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(id_catatan) FROM tabelcatatan", null));
    }
    */


    /**
     * ===================== AGENDA ===========================
     */

    public void insertagenda(AgendaClassData classdata) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();

        cv.put("id_agenda", classdata.getId_agenda());
        cv.put("acara", classdata.getAcara());
        cv.put("tanggal", classdata.getTanggal());
        cv.put("lokasi", classdata.getLokasi());
        cv.put("jammasuk", classdata.getJammasuk());
        cv.put("ruang", classdata.getRuang());
        cv.put("orang", classdata.getOrang());
        cv.put("pengingat", classdata.getPengingat());

        db.insert("tabelagenda", null, cv);
        db.close();
    }

    public void updateagenda(AgendaClassData classdata) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        //     cv.put("id_agenda", classdata.getid_agenda());
        cv.put("acara", classdata.getAcara());
        cv.put("tanggal", classdata.getTanggal());
        cv.put("lokasi", classdata.getLokasi());
        cv.put("jammasuk", classdata.getJammasuk());
        cv.put("ruang", classdata.getRuang());
        cv.put("orang", classdata.getOrang());
        cv.put("pengingat", classdata.getPengingat());

        db.update("tabelagenda", cv, "id_agenda = ?", new String[]{String.valueOf(classdata.getId_agenda())});
        db.close();
    }

    public List<AgendaClassData> getallagenda() {
        List<AgendaClassData> dataArray = new ArrayList<AgendaClassData>();
        String query = "SELECT * FROM tabelagenda" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null );
        if (cursor.moveToFirst()) {
            do {
                AgendaClassData datalist = new AgendaClassData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                dataArray.add(datalist);
            } while (cursor.moveToNext());
        }
        return dataArray;
    }


    public void deleteagenda(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={id};
        ContentValues cv = new ContentValues();

        db.delete("tabelagenda", "id_agenda=?", args);
        db.close();

    }

    public Cursor cekagenda(String status){
        String[] args={status};
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(acara) FROM tabelagenda WHERE acara=?", args));
    }

    public String cekagenda0(Cursor c){
        return(c.getString(0));
    }

    /**
     * ===================== UJIAN ===========================
     */


    public void insertujian(UjianClassData classdata) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("id_ujian", classdata.getId_ujian());
        cv.put("ujian", classdata.getUjian());
        cv.put("tanggal", classdata.getTanggal());
        cv.put("gedung", classdata.getGedung());
        cv.put("jammasuk", classdata.getJammasuk());
        cv.put("ruang", classdata.getRuang());
        cv.put("dosen", classdata.getDosen());
        cv.put("pengingat", classdata.getPengingat());

        db.insert("tabelujian", null, cv);
        db.close();
    }

    public List<UjianClassData> getallujian() {
        List<UjianClassData> dataArray = new ArrayList<UjianClassData>();
        String query = "SELECT * FROM tabelujian" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null );
        if (cursor.moveToFirst()) {
            do {
                UjianClassData datalist = new UjianClassData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                dataArray.add(datalist);
            } while (cursor.moveToNext());
        }
        return dataArray;
    }

    public void updateujian(UjianClassData classdata) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ujian", classdata.getUjian());
        cv.put("tanggal", classdata.getTanggal());
        cv.put("gedung", classdata.getGedung());
        cv.put("jammasuk", classdata.getJammasuk());
        cv.put("ruang", classdata.getRuang());
        cv.put("dosen", classdata.getDosen());
        cv.put("pengingat", classdata.getPengingat());

        db.update("tabelujian", cv, "id_ujian" + " = ?", new String[]{String.valueOf(classdata.getId_ujian())});
        db.close();

    }

    public void deleteujian(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={id};
        ContentValues cv = new ContentValues();

        db.delete("tabelujian", "id_ujian=?", args);
        db.close();

    }

    public Cursor cekujian(String status){
        String[] args={status};
        return(getReadableDatabase()
                .rawQuery("SELECT COUNT(ujian) FROM tabelujian WHERE ujian=?", args));
    }

    public String cekujian0(Cursor c){
        return(c.getString(0));
    }

}
