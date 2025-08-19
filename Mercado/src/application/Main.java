package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            TelaLogin(); // abre direto o login
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---- Tela Login ----
    public static void TelaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/viewLogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Mercadinho - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Erro ao abrir TelaLogin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---- Abre qualquer tela ----
    public static void go(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Erro ao carregar FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
