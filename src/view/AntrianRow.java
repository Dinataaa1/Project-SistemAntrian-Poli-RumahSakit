package view;

import antrian.Antrian;
import poli.Poli;

/**
 * Data Transfer Object (DTO) untuk baris antrean pada TableView.
 * Menghubungkan informasi tiket Antrian dan Poli ke antarmuka pengguna,
 * dengan format nomor antrean rumah sakit standar (contoh: AA-001, BB-001).
 */
public class AntrianRow {
    private int nomor;
    private String kodeAntrian;
    private String nik;
    private String nama;
    private String jenisKelamin;
    private String poli;
    private String keluhan;
    private String status;
    private Antrian antrianRef;
    private Poli poliRef;

    public AntrianRow(Antrian antrian, String namaPoli, Poli poli, String status) {
        this.antrianRef = antrian;
        this.poliRef = poli;
        this.nomor = antrian.getNomorAntrian();
        this.nik = antrian.getPasien().getNik();
        this.nama = antrian.getPasien().getNama();
        this.jenisKelamin = antrian.getPasien().getJenisKelamin();
        this.poli = namaPoli;
        this.keluhan = antrian.getKeluhan();
        this.status = status;
        this.kodeAntrian = formatKodeAntrian(namaPoli, this.nomor);
    }

    /**
     * Menghasilkan kode antrean rumah sakit seperti AA-001, BB-001, CC-001.
     */
    public static String formatKodeAntrian(String namaPoli, int no) {
        String prefix = "AA";
        if (namaPoli != null) {
            if (namaPoli.toLowerCase().contains("umum")) {
                prefix = "AA";
            } else if (namaPoli.toLowerCase().contains("gigi")) {
                prefix = "BB";
            } else if (namaPoli.toLowerCase().contains("anak")) {
                prefix = "CC";
            }
        }
        return String.format("%s-%03d", prefix, no);
    }

    public int getNomor() {
        return nomor;
    }

    public String getKodeAntrian() {
        return kodeAntrian;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getPoli() {
        return poli;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Antrian getAntrianRef() {
        return antrianRef;
    }

    public Poli getPoliRef() {
        return poliRef;
    }
}
