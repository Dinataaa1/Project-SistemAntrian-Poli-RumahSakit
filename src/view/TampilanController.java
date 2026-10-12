package view;

import antrian.Antrian;
import model.Dokter;
import model.Pasien;
import poli.Poli;
import poli.PoliAnak;
import poli.PoliGigi;
import poli.PoliUmum;
import rumahsakit.RumahSakit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

/**
 * Controller untuk UI Sistem Antrean Poliklinik Rumah Sakit.
 * Menghubungkan tampilan FXML dengan logika bisnis asli (RumahSakit, Poli, Pasien, Antrian).
 */
public class TampilanController implements Initializable {

    // --- Form Pendaftaran Pasien ---
    @FXML private TextField txtNik;
    @FXML private TextField txtNamaPasien;
    @FXML private ComboBox<String> comboJenisKelamin;
    @FXML private ComboBox<String> comboPoliDaftar;
    @FXML private TextField txtKeluhan;
    @FXML private Button btnTambahAntrian;

    // --- Kontrol & Display Antrean ---
    @FXML private ComboBox<String> comboPoliAktif;
    @FXML private Label lblDokterInfo;
    @FXML private Label lblNomorAntrian;
    @FXML private Label lblPasienAktif;
    @FXML private Button btnPanggil;
    @FXML private Button btnProses;

    // --- Tabel Antrean Keseluruhan ---
    @FXML private TableView<AntrianRow> tableAntrian;
    @FXML private TableColumn<AntrianRow, String> colNo;
    @FXML private TableColumn<AntrianRow, String> colNik;
    @FXML private TableColumn<AntrianRow, String> colNama;
    @FXML private TableColumn<AntrianRow, String> colJenisKelamin;
    @FXML private TableColumn<AntrianRow, String> colPoli;
    @FXML private TableColumn<AntrianRow, String> colKeluhan;
    @FXML private TableColumn<AntrianRow, String> colStatus;

    // --- Data Model & State ---
    private RumahSakit rs;
    private PoliUmum poliUmum;
    private PoliGigi poliGigi;
    private PoliAnak poliAnak;
    private Map<String, Poli> poliMap = new LinkedHashMap<>();
    private Map<String, String> dokterInfoMap = new HashMap<>();

    private ObservableList<AntrianRow> listAntrianTabel = FXCollections.observableArrayList();
    private AntrianRow pasienSedangDipanggil = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSistemDanModel();
        initFormComponents();
        initTableColumns();
        preloadDataAwal();
    }

    /**
     * Inisialisasi model sesuai skenario alur project utama.
     */
    private void initSistemDanModel() {
        rs = RumahSakit.getInstance();

        Dokter dokterUmum = new Dokter("dr. Budi", "Dokter Umum", "198501012010011001");
        Dokter dokterGigi = new Dokter("drg. Sari", "Dokter Gigi", "198703152012022002");
        Dokter dokterAnak = new Dokter("dr. Rina, Sp.A", "Spesialis Anak", "198906202014032003");

        poliUmum = new PoliUmum("Poli Umum", dokterUmum);
        poliGigi = new PoliGigi("Poli Gigi", dokterGigi);
        poliAnak = new PoliAnak("Poli Anak", dokterAnak);

        rs.tambahPoli(poliUmum);
        rs.tambahPoli(poliGigi);
        rs.tambahPoli(poliAnak);

        poliMap.put("Poli Umum", poliUmum);
        poliMap.put("Poli Gigi", poliGigi);
        poliMap.put("Poli Anak", poliAnak);

        dokterInfoMap.put("Poli Umum", "dr. Budi (Dokter Umum) - NIP: 198501012010011001");
        dokterInfoMap.put("Poli Gigi", "drg. Sari (Dokter Gigi) - NIP: 198703152012022002");
        dokterInfoMap.put("Poli Anak", "dr. Rina, Sp.A (Spesialis Anak) - NIP: 198906202014032003");
    }

    /**
     * Inisialisasi pilihan dropdown ComboBox.
     */
    private void initFormComponents() {
        comboJenisKelamin.setItems(FXCollections.observableArrayList("Laki-laki", "Perempuan"));
        comboJenisKelamin.getSelectionModel().selectFirst();

        ObservableList<String> daftarPoliList = FXCollections.observableArrayList(poliMap.keySet());
        comboPoliDaftar.setItems(daftarPoliList);
        comboPoliDaftar.getSelectionModel().selectFirst();

        comboPoliAktif.setItems(daftarPoliList);
        comboPoliAktif.getSelectionModel().selectFirst();

        comboPoliAktif.setOnAction(e -> handleGantiPoliAktif());
        handleGantiPoliAktif();
    }

    /**
     * Konfigurasi cell value factory untuk TableView.
     */
    private void initTableColumns() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("kodeAntrian"));
        colNik.setCellValueFactory(new PropertyValueFactory<>("nik"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colJenisKelamin.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colPoli.setCellValueFactory(new PropertyValueFactory<>("poli"));
        colKeluhan.setCellValueFactory(new PropertyValueFactory<>("keluhan"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Format visual badge/warna untuk kolom status
        colStatus.setCellFactory(column -> new TableCell<AntrianRow, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equalsIgnoreCase("Menunggu")) {
                        setStyle("-fx-text-fill: #b45309; -fx-font-weight: bold;");
                    } else if (item.equalsIgnoreCase("Sedang Diperiksa")) {
                        setStyle("-fx-text-fill: #1d4ed8; -fx-font-weight: bold;");
                    } else if (item.equalsIgnoreCase("Selesai")) {
                        setStyle("-fx-text-fill: #059669; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #475569;");
                    }
                }
            }
        });

        tableAntrian.setItems(listAntrianTabel);
    }

    /**
     * Memuat data antrean awal sesuai skenario Main.java agar status selaras dengan alur proyek.
     */
    private void preloadDataAwal() {
        Pasien andi  = new Pasien("3578010101900001", "Andi", "Laki-laki");
        Pasien bunga = new Pasien("3578015505920002", "Bunga", "Perempuan");
        Pasien cahyo = new Pasien("3578010202850003", "Cahyo", "Laki-laki");
        Pasien dita  = new Pasien("3578014404180004", "Dita", "Perempuan");

        daftarkanPasienInternal(andi, poliUmum, "Poli Umum", "Demam");
        daftarkanPasienInternal(bunga, poliUmum, "Poli Umum", "Batuk");
        daftarkanPasienInternal(cahyo, poliGigi, "Poli Gigi", "Sakit gigi");
        daftarkanPasienInternal(andi,  poliGigi, "Poli Gigi", "Gusi bengkak");
        daftarkanPasienInternal(dita,  poliAnak, "Poli Anak", "Demam");
    }

    private void daftarkanPasienInternal(Pasien pasien, Poli targetPoli, String namaPoli, String keluhan) {
        rs.tambahPasienKeAntrian(pasien, targetPoli, keluhan);
        int nomorUrut = hitungNomorUrut(namaPoli);
        Antrian tiket = new Antrian(nomorUrut, pasien, keluhan);
        AntrianRow row = new AntrianRow(tiket, namaPoli, targetPoli, "Menunggu");
        listAntrianTabel.add(row);
    }

    private int hitungNomorUrut(String namaPoli) {
        int count = 0;
        for (AntrianRow r : listAntrianTabel) {
            if (r.getPoli().equalsIgnoreCase(namaPoli)) {
                count++;
            }
        }
        return count + 1;
    }

    /**
     * Handler saat pengguna mengganti pilihan poli aktif di panel kontrol.
     */
    private void handleGantiPoliAktif() {
        String poliTerpilih = comboPoliAktif.getValue();
        if (poliTerpilih != null) {
            lblDokterInfo.setText(dokterInfoMap.getOrDefault(poliTerpilih, "-"));

            // Cek apakah ada pasien yang sedang dipanggil di poli ini
            AntrianRow aktif = cariPasienStatus(poliTerpilih, "Sedang Diperiksa");
            if (aktif != null) {
                lblNomorAntrian.setText(aktif.getKodeAntrian());
                lblPasienAktif.setText(aktif.getNama() + " (" + aktif.getKeluhan() + ")");
                pasienSedangDipanggil = aktif;
            } else {
                lblNomorAntrian.setText("-");
                lblPasienAktif.setText("Belum ada antrean yang dipanggil");
                pasienSedangDipanggil = null;
            }
        }
    }

    /**
     * Event Handler: Tambah Pasien ke Antrean.
     */
    @FXML
    public void handleTambahAntrian(ActionEvent event) {
        String nik = txtNik.getText() != null ? txtNik.getText().trim() : "";
        String nama = txtNamaPasien.getText() != null ? txtNamaPasien.getText().trim() : "";
        String jk = comboJenisKelamin.getValue();
        String namaPoli = comboPoliDaftar.getValue();
        String keluhan = txtKeluhan.getText() != null ? txtKeluhan.getText().trim() : "";

        if (nik.isEmpty()) {
            tampilkanPeringatan("Validasi Input", "NIK pasien wajib diisi!");
            txtNik.requestFocus();
            return;
        }

        if (nama.isEmpty()) {
            tampilkanPeringatan("Validasi Input", "Nama pasien wajib diisi!");
            txtNamaPasien.requestFocus();
            return;
        }

        if (keluhan.isEmpty()) {
            tampilkanPeringatan("Validasi Input", "Keluhan singkat wajib diisi!");
            txtKeluhan.requestFocus();
            return;
        }

        Poli targetPoli = poliMap.get(namaPoli);
        if (targetPoli == null) {
            tampilkanPeringatan("Kesalahan", "Poli tujuan tidak valid.");
            return;
        }

        Pasien pasienBaru = new Pasien(nik, nama, jk);
        daftarkanPasienInternal(pasienBaru, targetPoli, namaPoli, keluhan);

        // Ambil baris antrean terakhir untuk info kode
        AntrianRow barisTerakhir = listAntrianTabel.get(listAntrianTabel.size() - 1);

        // Reset form input
        txtNik.clear();
        txtNamaPasien.clear();
        txtKeluhan.clear();

        tampilkanInformasi("Pendaftaran Berhasil", 
                "Pasien " + nama + " berhasil didaftarkan!\nNomor Antrean: " + barisTerakhir.getKodeAntrian() + " (" + namaPoli + ")");
    }

    /**
     * Event Handler: Panggil Antrean Berikutnya.
     * Menjalankan poli.panggilAntrian() sesuai logika polimorfik aslinya.
     */
    @FXML
    public void handlePanggilAntrian(ActionEvent event) {
        String poliTerpilih = comboPoliAktif.getValue();
        if (poliTerpilih == null) return;

        Poli targetPoli = poliMap.get(poliTerpilih);
        if (targetPoli == null) return;

        // Cari pasien status "Menunggu" pertama di poli ini (FIFO)
        AntrianRow antrianBerikutnya = cariPasienStatus(poliTerpilih, "Menunggu");

        if (antrianBerikutnya == null) {
            // Jalankan method asli poli untuk mencetak pesan console
            targetPoli.panggilAntrian();
            tampilkanPeringatan("Antrean Kosong", "Tidak ada antrean yang menunggu di " + poliTerpilih);
            return;
        }

        // Jalankan method asli poli (mencetak output polimorfik ke konsol)
        System.out.println("\n[Sistem UI memanggil antrean]:");
        targetPoli.panggilAntrian();

        // Update status di UI
        antrianBerikutnya.setStatus("Sedang Diperiksa");
        pasienSedangDipanggil = antrianBerikutnya;

        lblNomorAntrian.setText(antrianBerikutnya.getKodeAntrian());
        lblPasienAktif.setText(antrianBerikutnya.getNama() + " (" + antrianBerikutnya.getKeluhan() + ")");

        tableAntrian.refresh();
    }

    /**
     * Event Handler: Proses Pemeriksaan.
     * Menjalankan poli.prosesPemeriksaan() sesuai logika polimorfik aslinya.
     */
    @FXML
    public void handleProsesPemeriksaan(ActionEvent event) {
        String poliTerpilih = comboPoliAktif.getValue();
        if (poliTerpilih == null) return;

        Poli targetPoli = poliMap.get(poliTerpilih);
        if (targetPoli == null) return;

        // Pasien yang sedang dipanggil atau pasien pertama yang menunggu
        AntrianRow pasienDiproses = pasienSedangDipanggil;
        if (pasienDiproses == null || !pasienDiproses.getPoli().equalsIgnoreCase(poliTerpilih)) {
            pasienDiproses = cariPasienStatus(poliTerpilih, "Sedang Diperiksa");
        }

        if (pasienDiproses == null) {
            pasienDiproses = cariPasienStatus(poliTerpilih, "Menunggu");
        }

        if (pasienDiproses == null) {
            tampilkanPeringatan("Pemeriksaan", "Tidak ada pasien yang dapat diproses di " + poliTerpilih);
            return;
        }

        // Jalankan method asli poli (mencetak pemeriksaan polimorfik ke konsol)
        System.out.println("\n[Sistem UI memproses pemeriksaan]:");
        targetPoli.prosesPemeriksaan();

        // Update status di UI menjadi Selesai
        pasienDiproses.setStatus("Selesai");
        pasienSedangDipanggil = null;

        lblNomorAntrian.setText("-");
        lblPasienAktif.setText("Pemeriksaan selesai. Menunggu antrean berikutnya.");

        tableAntrian.refresh();
        tampilkanInformasi("Pemeriksaan Selesai", 
                "Pemeriksaan pasien " + pasienDiproses.getNama() + " (" + pasienDiproses.getKodeAntrian() + ") di " + poliTerpilih + " telah selesai.");
    }

    private AntrianRow cariPasienStatus(String namaPoli, String status) {
        for (AntrianRow row : listAntrianTabel) {
            if (row.getPoli().equalsIgnoreCase(namaPoli) && row.getStatus().equalsIgnoreCase(status)) {
                return row;
            }
        }
        return null;
    }

    private void tampilkanPeringatan(String judul, String pesan) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    private void tampilkanInformasi(String judul, String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}
