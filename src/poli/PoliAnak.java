package poli;

import antrian.Antrian;
import model.Dokter;

/**
 * Poli untuk pemeriksaan kesehatan anak.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class PoliAnak extends Poli {

    /**
     * Membuat poli anak.
     *
     * @param namaPoli nama poli
     * @param dokter   dokter penanggung jawab
     */
    public PoliAnak(String namaPoli, Dokter dokter) {
        super(namaPoli, dokter);
    }

    /** {@inheritDoc} Pasien masuk ruang periksa anak. */
    @Override
    public void panggilAntrian() {
        Antrian a = daftarAntrian.peek();
        if (a == null) {
            System.out.println("[" + namaPoli + "] Tidak ada antrian.");
            return;
        }
        System.out.println("[" + namaPoli + "] Pasien anak masuk ruang periksa anak");
        System.out.println("   Nomor antrian : " + a.getNomorAntrian());
        System.out.println("   Nama          : " + a.getPasien().getNama());
        System.out.println("   NIK           : " + a.getPasien().getNik());
        System.out.println("   Jenis kelamin : " + a.getPasien().getJenisKelamin());
        System.out.println("   Keluhan       : " + a.getKeluhan());
        System.out.println("   Dokter        : " + dokter.getNamaDokter() + " (" + dokter.getSpesialis() + ") " + dokter.getNip());
        System.out.println("   Hari          : " + a.getHari());
    }

    /** {@inheritDoc} Pemeriksaan khas: pemeriksaan tumbuh kembang anak. */
    @Override
    public void prosesPemeriksaan() {
        Antrian a = daftarAntrian.poll();
        if (a == null) return;
        System.out.println("   -> " + dokter.getNamaDokter() + " memeriksa tumbuh kembang anak " + a.getPasien().getNama());
    }
}
