package poli;

import antrian.Antrian;
import java.util.LinkedList;
import java.util.Queue;
import model.Dokter;
import model.Pasien;

/**
 * Class abstrak induk semua poliklinik (abstraksi dan pewarisan).
 * <p>
 * Menyimpan nama poli, dokter penanggung jawab, dan antrean pasien
 * berstruktur {@link Queue} (FIFO). Subclass wajib mengimplementasikan
 * {@link #panggilAntrian()} dan {@link #prosesPemeriksaan()} sesuai
 * kekhasan poli masing-masing (polimorfisme).
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public abstract class Poli {
    /** Nama poli, misalnya "Poli Umum". */
    protected String namaPoli;
    /** Dokter penanggung jawab poli ini. */
    protected Dokter dokter;
    /** Antrean tiket pasien dengan urutan FIFO. */
    protected Queue<Antrian> daftarAntrian = new LinkedList<>();
    /** Nomor antrean terakhir yang sudah diberikan. */
    protected int nomorTerakhir = 0;

    /**
     * Membuat poli baru.
     *
     * @param namaPoli nama poli
     * @param dokter   dokter penanggung jawab
     */
    public Poli(String namaPoli, Dokter dokter) {
        this.namaPoli = namaPoli;
        this.dokter = dokter;
    }

    /**
     * Mendaftarkan pasien ke antrean poli. Nomor antrean naik otomatis dan
     * sebuah tiket {@link Antrian} dimasukkan ke akhir antrean.
     *
     * @param pasien  pasien yang mendaftar
     * @param keluhan keluhan pasien
     */
    public void tambahPasienKeAntrian(Pasien pasien, String keluhan) {
        nomorTerakhir++;
        daftarAntrian.add(new Antrian(nomorTerakhir, pasien, keluhan));
    }

    /** Mencetak dokter dan seluruh antrean yang masih menunggu di poli ini. */
    public void tampilkanAntrian() {
        System.out.println("[" + namaPoli + "] Dokter: " + dokter.getNamaDokter()
                + " (" + dokter.getSpesialis() + ") " + dokter.getNip());
        if (daftarAntrian.isEmpty()) {
            System.out.println("   (antrian kosong)");
        }
        for (Antrian a : daftarAntrian) {
            System.out.println("   No. " + a.getNomorAntrian() + " - " + a.getPasien().getNama());
        }
    }

    /**
     * Memanggil pasien terdepan masuk ruangan dan menampilkan datanya.
     * Pasien tidak dihapus dari antrean (memakai {@code peek()}).
     */
    public abstract void panggilAntrian();

    /**
     * Memeriksa pasien terdepan lalu mengeluarkannya dari antrean
     * (memakai {@code poll()}). Tidak melakukan apa-apa jika antrean kosong.
     */
    public abstract void prosesPemeriksaan();
}
