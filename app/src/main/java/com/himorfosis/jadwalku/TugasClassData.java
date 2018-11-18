package com.himorfosis.jadwalku;

public class TugasClassData {

    private Integer id_tugas;
    private String tugas;
    private String keterangan;
    private String tanggal;
    private String jammasuk;
    private String gedung;
    private String ruang;
    private String dosen;
    private String pengingat;
    private byte[] gambar;


    public TugasClassData( Integer id_tugas, String tugas, String keterangan, String tanggal, String jammasuk, String gedung, String dosen, byte[] gambar, String pengingat) {

        this.id_tugas = id_tugas;
        this.tugas = tugas;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.jammasuk = jammasuk;
        this.gedung = gedung;
        this.dosen = dosen;
        this.gambar = gambar;
        this.pengingat = pengingat;
    }

    public Integer getId_tugas() {
        return id_tugas;
    }

    public void setId_tugas(Integer id_tugas) {
        this.id_tugas = id_tugas;
    }

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJammasuk() {
        return jammasuk;
    }

    public void setJammasuk(String jammasuk) {
        this.jammasuk = jammasuk;
    }

    public String getGedung() {
        return gedung;
    }

    public void setGedung(String gedung) {
        this.gedung = gedung;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getPengingat() {
        return pengingat;
    }

    public void setPengingat(String pengingat) {
        this.pengingat = pengingat;
    }

    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }
}
