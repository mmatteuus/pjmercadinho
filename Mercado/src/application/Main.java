package application;

import controller.controllerMainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Funcionario;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    private static Scene main;

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/view/viewLogin.fxml"));
            main = new Scene(fxmlLogin);
            stage.setTitle("Mercadinho - Login");
            stage.setScene(main);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // AQUI: com passagem de Funcionario
    public static void TelaHome(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/viewMainMenu.fxml"));
        Parent telaHome = loader.load();

        controllerMainMenu controller = loader.getController();
        controller.setFuncionario(funcionario);

        main = new Scene(telaHome);
        stage.setTitle("Mercadinho - Menu Principal");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaLogin() throws IOException {
        Parent telaLogin = FXMLLoader.load(Main.class.getResource("/view/viewLogin.fxml"));
        main = new Scene(telaLogin);
        stage.setTitle("Mercadinho - Login");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
