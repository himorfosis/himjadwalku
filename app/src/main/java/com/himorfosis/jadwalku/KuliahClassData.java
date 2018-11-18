package com.himorfosis.jadwalku;

public class KuliahClassData {

    private Integer idkuliah;
    private String matakuliah;
    private String tanggalmulai;
    private String tanggalselesai;
    private String jammulai;
    private String lokasi;
    private String ruang;
    private String dosen;
    private String pengingat;


    public KuliahClassData(Integer idkuliah, String matakuliah, String tanggalmulai, String tanggalselesai, String jammulai, String lokasi, String ruang, String dosen, String pengingat) {

        this.idkuliah = idkuliah;
        this.matakuliah = matakuliah;
        this.tanggalmulai = tanggalmulai;
        this.tanggalselesai = tanggalselesai;
        this.jammulai = jammulai;
        this.lokasi = lokasi;
        this.ruang = ruang;
        this.dosen = dosen;
        this.pengingat = pengingat;

    }


    public Integer getIdkuliah() {
        return idkuliah;
    }

    public void setIdkuliah(Integer idkuliah) {
        this.idkuliah = idkuliah;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }

    public String getTanggalmulai() {
        return tanggalmulai;
    }

    public void setTanggalmulai(String tanggalmulai) {
        this.tanggalmulai = tanggalmulai;
    }

    public String getTanggalselesai() {
        return tanggalselesai;
    }

    public void setTanggalselesai(String tanggalselesai) {
        this.tanggalselesai = tanggalselesai;
    }

    public String getJammulai() {
        return jammulai;
    }

    public void setJammulai(String jammulai) {
        this.jammulai = jammulai;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
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
