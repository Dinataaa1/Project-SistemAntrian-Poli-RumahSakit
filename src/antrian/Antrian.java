package antrian;

import model.Pasien;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Tiket antrean: satu objek mewakili satu kunjungan pasien ke sebuah poli.
 * <p>
 * Class ini menggabungkan objek {@link Pasien} dengan data kunjungan
 * (nomor, keluhan, tanggal), sehingga data identitas pasien tidak ikut berubah
 * ketika pasien mendaftar ke poli lain.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class Antrian {
    private int nomorAntrian;
    private Pasien pasien;
    private String keluhan;
    private LocalDate tanggal;

    /**
     * Membuat tiket antrean baru. Tanggal kunjungan diambil otomatis dari
     * tanggal sistem saat tiket dibuat.
     *
     * @param nomorAntrian nomor urut antrean di poli tujuan
     * @param pasien       pasien pemilik tiket
     * @param keluhan      keluhan pasien pada kunjungan ini
     */
    public Antrian(int nomorAntrian, Pasien pasien, String keluhan) {
        this.nomorAntrian = nomorAntrian;
        this.pasien = pasien;
        this.keluhan = keluhan;
        this.tanggal = LocalDate.now();
    }

    /**
     * Mengambil nomor urut antrean.
     *
     * @return nomor urut antrean
     */
    public int getNomorAntrian() { return nomorAntrian; }

    /**
     * Mengambil pasien pemilik tiket.
     *
     * @return pasien pemilik tiket
     */
    public Pasien getPasien() { return pasien; }

    /**
     * Mengambil keluhan pasien.
     *
     * @return keluhan pasien
     */
    public String getKeluhan() { return keluhan; }

    /**
     * Mengambil tanggal kunjungan.
     *
     * @return tanggal kunjungan
     */
    public LocalDate getTanggal() { return tanggal; }

    /**
     * Menghitung nama hari dari tanggal kunjungan.
     *
     * @return nama hari dalam bahasa Indonesia, misalnya "Senin"
     */
    public String getHari() {
        return tanggal.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("id-ID"));
    }
}
