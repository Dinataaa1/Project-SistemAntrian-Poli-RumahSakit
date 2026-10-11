package poli;

import antrian.Antrian;
import model.Dokter;

/**
 * Poli untuk pemeriksaan kesehatan gigi dan mulut.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class PoliGigi extends Poli {

    /**
     * Membuat poli gigi.
     *
     * @param namaPoli nama poli
     * @param dokter   dokter penanggung jawab
     */
    public PoliGigi(String namaPoli, Dokter dokter) {
        super(namaPoli, dokter);
    }

    /** {@inheritDoc} Pasien masuk ruang periksa gigi. */
    @Override
    public void panggilAntrian() {
        Antrian a = daftarAntrian.peek();
        if (a == null) {
            System.out.println("[" + namaPoli + "] Tidak ada antrian.");
            return;
        }
        System.out.println("[" + namaPoli + "] Pasien masuk ruang periksa gigi");
        System.out.println("   Nomor antrian : " + a.getNomorAntrian());
        System.out.println("   Nama          : " + a.getPasien().getNama());
        System.out.println("   NIK           : " + a.getPasien().getNik());
        System.out.println("   Jenis kelamin : " + a.getPasien().getJenisKelamin());
        System.out.println("   Keluhan       : " + a.getKeluhan());
        System.out.println("   Dokter        : " + dokter.getNamaDokter() + " (" + dokter.getSpesialis() + ") " + dokter.getNip());
        System.out.println("   Hari          : " + a.getHari());
    }

    /** {@inheritDoc} Pemeriksaan khas: pemeriksaan kondisi gigi. */
    @Override
    public void prosesPemeriksaan() {
        Antrian a = daftarAntrian.poll();
        if (a == null) return;
        System.out.println("   -> " + dokter.getNamaDokter() + " memeriksa kondisi gigi " + a.getPasien().getNama());
    }
}
