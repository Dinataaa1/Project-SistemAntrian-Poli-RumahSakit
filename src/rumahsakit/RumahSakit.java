package rumahsakit;

import java.util.ArrayList;
import java.util.List;
import model.Pasien;
import poli.Poli;

/**
 * Pusat manajemen rumah sakit dengan pola Singleton.
 * <p>
 * Hanya ada satu objek {@code RumahSakit} selama program berjalan.
 * Class ini mengelola daftar seluruh {@link Poli} (agregasi) dan
 * meneruskan perintah pendaftaran serta monitoring ke tiap poli.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class RumahSakit {
    private static RumahSakit instance;

    private String namaRS;
    private List<Poli> daftarPoli = new ArrayList<>();

    /** Constructor private agar objek tidak bisa dibuat dari luar class. */
    private RumahSakit() {
        this.namaRS = "RS Sehat Sentosa";
    }

    /**
     * Mengambil satu-satunya objek RumahSakit; dibuat saat pertama kali dipanggil.
     *
     * @return instance tunggal RumahSakit
     */
    public static RumahSakit getInstance() {
        if (instance == null) {
            instance = new RumahSakit();
        }
        return instance;
    }

    /**
     * Mendaftarkan sebuah poli ke rumah sakit.
     *
     * @param poli poli yang ditambahkan
     */
    public void tambahPoli(Poli poli) {
        daftarPoli.add(poli);
    }

    /**
     * Mendaftarkan pasien ke antrean poli tujuan.
     *
     * @param pasien  pasien yang mendaftar
     * @param poli    poli tujuan
     * @param keluhan keluhan pasien
     */
    public void tambahPasienKeAntrian(Pasien pasien, Poli poli, String keluhan) {
        poli.tambahPasienKeAntrian(pasien, keluhan);
    }

    /** Menampilkan antrean seluruh poli (monitoring terpusat). */
    public void tampilkanAntrian() {
        System.out.println("=== Antrian " + namaRS + " ===");
        for (Poli poli : daftarPoli) {
            poli.tampilkanAntrian();
        }
    }
}
