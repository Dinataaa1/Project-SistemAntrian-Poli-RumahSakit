package model;

/**
 * Menyimpan data identitas pasien.
 * <p>
 * Class ini berdiri sendiri (Single Responsibility): hanya berisi data pribadi,
 * tanpa logika antrean. Data kunjungan disimpan terpisah di {@link antrian.Antrian}.
 *
 * @author Ragil Rohmat Dinata, Irtadho Zainul Falah, Prinsa Daffa Nanda A
 * @version 1.0
 */
public class Pasien {
    private String nik;
    private String nama;
    private String jenisKelamin;

    /**
     * Membuat objek pasien baru.
     *
     * @param nik          Nomor Induk Kependudukan pasien
     * @param nama         nama lengkap pasien
     * @param jenisKelamin jenis kelamin pasien ("Laki-laki" atau "Perempuan")
     */
    public Pasien(String nik, String nama, String jenisKelamin) {
        this.nik = nik;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
    }

    /**
     * Mengambil NIK pasien.
     *
     * @return NIK pasien
     */
    public String getNik() { return nik; }

    /**
     * Mengambil nama pasien.
     *
     * @return nama pasien
     */
    public String getNama() { return nama; }

    /**
     * Mengambil jenis kelamin pasien.
     *
     * @return jenis kelamin pasien
     */
    public String getJenisKelamin() { return jenisKelamin; }
}
