package com.himorfosis.jadwalku;

public class AgendaClassData {

    private Integer id_agenda;
    private String acara;
    private String tanggal;
    private String jammasuk;
    private String lokasi;
    private String ruang;
    private String orang;
    private String pengingat;


    public AgendaClassData(String pengingat) {

        this.pengingat = pengingat;

    }

    public AgendaClassData (Integer id_agenda, String acara, String tanggal, String jammasuk, String lokasi, String ruang, String orang, String pengingat) {

        this.id_agenda = id_agenda;
        this.acara = acara;
        this.tanggal = tanggal;
        this.jammasuk = jammasuk;
        this.lokasi = lokasi;
        this.ruang = ruang;
        this.orang = orang;
        this.pengingat = pengingat;
    }

    public Integer getId_agenda() {
        return id_agenda;
    }

    public void setId_agenda(Integer id_agenda) {
        this.id_agenda = id_agenda;
    }

    public String getAcara() {
        return acara;
    }

    public void setAcara(String acara) {
        this.acara = acara;
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

    public String getOrang() {
        return orang;
    }

    public void setOrang(String orang) {
        this.orang = orang;
    }

    public String getPengingat() {
        return pengingat;
    }

    public void setPengingat(String pengingat) {
        this.pengingat = pengingat;
    }
}
