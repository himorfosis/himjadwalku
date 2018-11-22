package com.himorfosis.jadwalku;

public class KehadiranClassData {

    private Integer id_kehadiran;
    private String kegiatan;
    private String status;
    private String waktu;

    public KehadiranClassData(Integer id_kehadiran, String kegiatan, String status, String waktu) {

        this.id_kehadiran = id_kehadiran;
        this.kegiatan = kegiatan;
        this.status = status;
        this.waktu = waktu;

    }


    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }


    public Integer getId_kehadiran() {
        return id_kehadiran;
    }

    public void setId_kehadiran(Integer id_kehadiran) {
        this.id_kehadiran = id_kehadiran;
    }
}
