import model.Dokter;
import model.Pasien;
import poli.Poli;
import poli.PoliAnak;
import poli.PoliUmum;
import rumahsakit.RumahSakit;

/**
 * Skenario tambahan untuk menguji Singleton, antrean kosong, dan urutan FIFO.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class DemoSkenario {
    /** Membuat objek DemoSkenario (tidak dipakai; program dimulai dari main). */
    public DemoSkenario() { }

    /**
     * Menjalankan tiga skenario uji.
     *
     * @param args tidak digunakan
     */
    public static void main(String[] args) {
        System.out.println("=== Skenario 1: Singleton ===");
        RumahSakit a = RumahSakit.getInstance();
        RumahSakit b = RumahSakit.getInstance();
        System.out.println("a == b ? " + (a == b));

        System.out.println("\n=== Skenario 2: Antrian kosong ===");
        PoliAnak poliAnak = new PoliAnak("Poli Anak", new Dokter("dr. Rina, Sp.A", "Spesialis Anak", "198906202014032003"));
        poliAnak.panggilAntrian();
        poliAnak.prosesPemeriksaan();
        poliAnak.tampilkanAntrian();

        System.out.println("\n=== Skenario 3: Urutan FIFO ===");
        PoliUmum poliUmum = new PoliUmum("Poli Umum", new Dokter("dr. Budi", "Dokter Umum", "198501012010011001"));
        a.tambahPoli(poliUmum);
        a.tambahPasienKeAntrian(new Pasien("3578010101900001", "Andi", "Laki-laki"), poliUmum, "Demam");
        a.tambahPasienKeAntrian(new Pasien("3578015505920002", "Bunga", "Perempuan"), poliUmum, "Batuk");
        a.tambahPasienKeAntrian(new Pasien("3578010202850003", "Cahyo", "Laki-laki"), poliUmum, "Pusing");
        a.tampilkanAntrian();
        for (int i = 1; i <= 3; i++) {
            System.out.println("-- Panggilan ke-" + i);
            poliUmum.panggilAntrian();
            poliUmum.prosesPemeriksaan();
        }
        a.tampilkanAntrian();
    }
}
