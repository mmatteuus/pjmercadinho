package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerLogin {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;

    @FXML
    private void actionButton() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        // TODO: Implementar validação de usuário/senha com banco de dados usando FuncionarioDAO
        if ("admin".equals(usuario) && "123".equals(senha)) {
            Main.go("/view/viewMainMenu.fxml", "Mercadinho - Menu Principal");
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText(null);
            alert.setContentText("Usuário ou senha inválidos!");
            alert.showAndWait();
        }
    }
}


