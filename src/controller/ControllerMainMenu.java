package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import model.Funcionario;

public class ControllerMainMenu {

    @FXML private Label txtUser;

    @FXML
    private void initialize() {
        // Mostra nome do usuário logado
        Funcionario usuarioLogado = Main.usuarioLogado;
        if (usuarioLogado != null && txtUser != null) {
            txtUser.setText(usuarioLogado.getNomeFuncionario());
        }
    }

    @FXML private void onNavRegistrarVenda(ActionEvent e) {
        navigateTo("/view/viewCadastroVenda.fxml", "Mercadinho - Registrar Venda");
    }

    @FXML private void onNavMenuPrincipal(ActionEvent e) {
        navigateTo("/view/viewMainMenu.fxml", "Mercadinho - Menu Principal");
    }

    @FXML private void onNavClientes(ActionEvent e) {
        navigateTo("/view/viewRelatorioCliente.fxml", "Mercadinho - Clientes"); // Corrigido para viewRelatorioCliente.fxml
    }

    @FXML private void onNavProdutos(ActionEvent e) {
        navigateTo("/view/viewRelatorioProdutos.fxml", "Mercadinho - Produtos");
    }

    @FXML private void onNavVendas(ActionEvent e) {
        navigateTo("/view/viewRelatorioVenda.fxml", "Mercadinho - Vendas");
    }

    @FXML private void onNavFuncionarios(ActionEvent e) {
        navigateTo("/view/viewRelatorioFuncionario.fxml", "Mercadinho - Funcionários");
    }

    @FXML private void onNavSair(ActionEvent e) {
        Main.TelaLogin();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Main.go(fxmlPath, title);
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


