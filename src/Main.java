import model.Dokter;
import model.Pasien;
import poli.Poli;
import poli.PoliAnak;
import poli.PoliGigi;
import poli.PoliUmum;
import rumahsakit.RumahSakit;

/**
 * Program utama: membuat dokter, pasien, dan poli, mendaftarkan pasien,
 * lalu memproses antrean secara polimorfik.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class Main {
    /** Membuat objek Main (tidak dipakai; program dimulai dari main). */
    public Main() { }

    /**
     * Titik masuk program.
     *
     * @param args tidak digunakan
     */
    public static void main(String[] args) {
        RumahSakit rs = RumahSakit.getInstance();

        // Dokter dan Pasien dibuat terpisah (berdiri sendiri)
        Dokter dokterUmum = new Dokter("dr. Budi", "Dokter Umum", "198501012010011001");
        Dokter dokterGigi = new Dokter("drg. Sari", "Dokter Gigi", "198703152012022002");
        Dokter dokterAnak = new Dokter("dr. Rina, Sp.A", "Spesialis Anak", "198906202014032003");

        Pasien andi  = new Pasien("3578010101900001", "Andi", "Laki-laki");
        Pasien bunga = new Pasien("3578015505920002", "Bunga", "Perempuan");
        Pasien cahyo = new Pasien("3578010202850003", "Cahyo", "Laki-laki");
        Pasien dita  = new Pasien("3578014404180004", "Dita", "Perempuan");

        PoliUmum poliUmum = new PoliUmum("Poli Umum", dokterUmum);
        PoliGigi poliGigi = new PoliGigi("Poli Gigi", dokterGigi);
        PoliAnak poliAnak = new PoliAnak("Poli Anak", dokterAnak);

        rs.tambahPoli(poliUmum);
        rs.tambahPoli(poliGigi);
        rs.tambahPoli(poliAnak);

        // Keluhan diinput saat mendaftar, hari diisi otomatis dari tanggal sistem
        rs.tambahPasienKeAntrian(andi,  poliUmum, "Demam");
        rs.tambahPasienKeAntrian(bunga, poliUmum, "Batuk");
        rs.tambahPasienKeAntrian(cahyo, poliGigi, "Sakit gigi");
        rs.tambahPasienKeAntrian(andi,  poliGigi, "Gusi bengkak");  // Andi yang sama, poli lain
        rs.tambahPasienKeAntrian(dita,  poliAnak, "Demam");

        rs.tampilkanAntrian();

        // Polymorphism: tipe sama (Poli), perilaku berbeda tiap poli
        System.out.println("\n=== Pasien Masuk Ruangan ===");
        Poli[] semuaPoli = { poliUmum, poliGigi, poliAnak };
        for (Poli poli : semuaPoli) {
            poli.panggilAntrian();
            poli.prosesPemeriksaan();
            System.out.println();
        }

        rs.tampilkanAntrian();
    }
}
