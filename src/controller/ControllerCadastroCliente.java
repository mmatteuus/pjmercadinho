package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import model.Funcionario;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCadastroCliente implements Initializable {

    @FXML private Label lblUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // mostra nome do usuário logado
        Funcionario f = Main.usuarioLogado;
        if (f != null && lblUser != null) {
            lblUser.setText(f.getNomeFuncionario());
        }
    }

    // Navegação lateral
    @FXML private void onNavRegistrarVenda(ActionEvent e) { navigateTo("/view/viewCadastroVenda.fxml", "Mercadinho - Registrar Venda"); }
    @FXML private void onNavMenu(ActionEvent e)           { navigateTo("/view/viewMainMenu.fxml", "Mercadinho - Menu Principal"); }
    @FXML private void onNavClientes(ActionEvent e)       { navigateTo("/view/viewCadrastroCliente.fxml", "Mercadinho - Cadastro Cliente"); }
    @FXML private void onNavProdutos(ActionEvent e)       { navigateTo("/view/viewRelatorioProdutos.fxml", "Mercadinho - Produtos"); }
    @FXML private void onNavVendas(ActionEvent e)         { navigateTo("/view/viewRelatorioVenda.fxml", "Mercadinho - Vendas"); }
    @FXML private void onNavFuncionarios(ActionEvent e)   { navigateTo("/view/viewRelatorioFuncionario.fxml", "Mercadinho - Funcionários"); }
    @FXML private void onNavSair(ActionEvent e)           { Main.TelaLogin(); }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Main.go(fxmlPath, title);
            // Adicionar feedback visual de sucesso, se necessário, mas geralmente a mudança de tela já é suficiente.
            // showAlert(Alert.AlertType.INFORMATION, "Navegação", "Tela " + title + " carregada com sucesso!");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Navegação");
            alert.setHeaderText("Não foi possível carregar a tela.");
            alert.setContentText("Ocorreu um erro ao tentar abrir a tela: " + title + ". Por favor, tente novamente.\nDetalhes: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace(); // Para depuração, remover em produção
        }
    }
}


