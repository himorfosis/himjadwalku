package com.himorfosis.jadwalku;

public class UjianClassData {

    private Integer id_ujian;
    private String ujian;
    private String tanggal;
    private String jammasuk;
    private String gedung;
    private String ruang;
    private String dosen;
    private String pengingat;


    public UjianClassData(Integer id_ujian, String ujian, String tanggal, String jammasuk, String gedung, String ruang, String dosen, String pengingat) {

        this.id_ujian = id_ujian;
        this.ujian = ujian;
        this.tanggal = tanggal;
        this.jammasuk = jammasuk;
        this.gedung = gedung;
        this.ruang = ruang;
        this.dosen = dosen;
        this.pengingat = pengingat;

    }

    public Integer getId_ujian() {
        return id_ujian;
    }

    public void setId_ujian(Integer id_ujian) {
        this.id_ujian = id_ujian;
    }

    public String getUjian() {
        return ujian;
    }

    public void setUjian(String ujian) {
        this.ujian = ujian;
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
}
