package poli;

import antrian.Antrian;
import model.Dokter;

/**
 * Poli untuk pemeriksaan kesehatan umum.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class PoliUmum extends Poli {

    /**
     * Membuat poli umum.
     *
     * @param namaPoli nama poli
     * @param dokter   dokter penanggung jawab
     */
    public PoliUmum(String namaPoli, Dokter dokter) {
        super(namaPoli, dokter);
    }

    /** {@inheritDoc} Pasien masuk ruang dokter umum. */
    @Override
    public void panggilAntrian() {
        Antrian a = daftarAntrian.peek();
        if (a == null) {
            System.out.println("[" + namaPoli + "] Tidak ada antrian.");
            return;
        }
        System.out.println("[" + namaPoli + "] Pasien masuk ruang dokter umum");
        System.out.println("   Nomor antrian : " + a.getNomorAntrian());
        System.out.println("   Nama          : " + a.getPasien().getNama());
        System.out.println("   NIK           : " + a.getPasien().getNik());
        System.out.println("   Jenis kelamin : " + a.getPasien().getJenisKelamin());
        System.out.println("   Keluhan       : " + a.getKeluhan());
        System.out.println("   Dokter        : " + dokter.getNamaDokter() + " (" + dokter.getSpesialis() + ") " + dokter.getNip());
        System.out.println("   Hari          : " + a.getHari());
    }

    /** {@inheritDoc} Pemeriksaan khas: pemeriksaan umum. */
    @Override
    public void prosesPemeriksaan() {
        Antrian a = daftarAntrian.poll();
        if (a == null) return;
        System.out.println("   -> " + dokter.getNamaDokter() + " melakukan pemeriksaan umum pada " + a.getPasien().getNama());
    }
}
