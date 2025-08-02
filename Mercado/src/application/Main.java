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
            showLogin();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLogin() {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/view/viewLogin.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMenuPrincipal() {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/view/viewMain.fxml"));
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
