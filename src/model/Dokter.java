package model;

/**
 * Menyimpan data dokter penanggung jawab sebuah poli.
 * <p>
 * Class ini berdiri sendiri dan tidak bergantung pada class lain.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class Dokter {
    private String namaDokter;
    private String spesialis;
    private String nip;

    /**
     * Membuat objek dokter baru.
     *
     * @param namaDokter nama dokter beserta gelar
     * @param spesialis  bidang spesialisasi dokter
     * @param nip        Nomor Induk Pegawai dokter
     */
    public Dokter(String namaDokter, String spesialis, String nip) {
        this.namaDokter = namaDokter;
        this.spesialis = spesialis;
        this.nip = nip;
    }

    /**
     * Mengambil nama dokter.
     *
     * @return nama dokter
     */
    public String getNamaDokter() { return namaDokter; }

    /**
     * Mengambil spesialisasi dokter.
     *
     * @return spesialisasi dokter
     */
    public String getSpesialis() { return spesialis; }

    /**
     * Mengambil NIP dokter.
     *
     * @return NIP dokter
     */
    public String getNip() { return nip; }
}
