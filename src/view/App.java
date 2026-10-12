package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Kelas utama aplikasi JavaFX untuk memuat Tampilan.fxml dan menampilkan
 * jendela utama.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlLocation = getClass().getResource("Tampilan.fxml");
            if (fxmlLocation == null) {
                fxmlLocation = getClass().getResource("/view/Tampilan.fxml");
            }
            if (fxmlLocation == null) {
                throw new IllegalStateException("File FXML 'Tampilan.fxml' tidak ditemukan di package view.");
            }

            Parent root = FXMLLoader.load(fxmlLocation);
            Scene scene = new Scene(root, 1060, 740);

            primaryStage.setTitle("Sistem Informasi & Antrean Poliklinik - RS Sehat Sentosa");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(960);
            primaryStage.setMinHeight(660);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
