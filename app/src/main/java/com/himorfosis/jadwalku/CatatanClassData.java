package com.himorfosis.jadwalku;

public class CatatanClassData {

    private String id_catatan;
    private String judul;
    private String isi;

    public CatatanClassData(String id_catatan, String judul, String isi) {

        this.id_catatan = id_catatan;
        this.judul = judul;
        this.isi = isi;
    }

    public String getId_catatan() {
        return id_catatan;
    }

    public void setId_catatan(String id_catatan) {
        this.id_catatan = id_catatan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
