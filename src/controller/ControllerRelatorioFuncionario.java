package controller;

import application.Main;
import dao.FuncionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import model.Funcionario;
import javafx.event.ActionEvent;

import java.util.List;

public class ControllerRelatorioFuncionario {

    @FXML private Label lblUser;
    @FXML private TextField txtBusca;
    @FXML private Button btnPesquisar, btnCadastrar, btnEditar, btnExcluir;
    @FXML private Button btnRegistrarVenda, btnMenuPrincipal, btnClientes, btnProdutos, btnVendas, btnFuncionarios, btnLogout;

    @FXML private TableView<Funcionario> tableFuncionarios;
    @FXML private TableColumn<Funcionario, String> colId, colNome, colCpf, colTelefone, colEmail;

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private ObservableList<Funcionario> funcionarios;

    @FXML
    public void initialize() {
        // Mostra nome do usuário logado
        Funcionario usuarioLogado = Main.usuarioLogado;
        if (usuarioLogado != null && lblUser != null) {
            lblUser.setText(usuarioLogado.getNomeFuncionario());
        }

        colId.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdFuncionario())));
        colNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNomeFuncionario()));
        colCpf.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCpfFuncionario()));
        colTelefone.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefone()));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));

        carregarFuncionarios();
        btnPesquisar.setOnAction(e -> onPesquisar());

        // Navegação lateral (adicionado para consistência)
        btnRegistrarVenda.setOnAction(e -> navigateTo("/view/viewCadastroVenda.fxml", "Mercadinho - Registrar Venda"));
        btnMenuPrincipal.setOnAction(e -> navigateTo("/view/viewMainMenu.fxml", "Mercadinho - Menu Principal"));
        btnClientes.setOnAction(e -> navigateTo("/view/viewCadrastroCliente.fxml", "Mercadinho - Clientes"));
        btnProdutos.setOnAction(e -> navigateTo("/view/viewRelatorioProdutos.fxml", "Mercadinho - Produtos"));
        btnVendas.setOnAction(e -> navigateTo("/view/viewRelatorioVenda.fxml", "Mercadinho - Vendas"));
        btnFuncionarios.setOnAction(e -> navigateTo("/view/viewRelatorioFuncionario.fxml", "Mercadinho - Funcionários"));
        btnLogout.setOnAction(e -> Main.TelaLogin());

        // Visibilidade do botão de funcionários
        if (usuarioLogado != null && !"ADMIN".equalsIgnoreCase(usuarioLogado.getNivel())) {
            btnFuncionarios.setVisible(false);
        }
    }

    private void carregarFuncionarios() {
        try {
            funcionarios = FXCollections.observableArrayList(funcionarioDAO.read());
            tableFuncionarios.setItems(funcionarios);
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Funcionários", "Não foi possível carregar a lista de funcionários.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onPesquisar() {
        String termo = txtBusca.getText() == null ? "" : txtBusca.getText().trim();
        try {
            if (termo.isEmpty()) {
                carregarFuncionarios();
            } else {
                funcionarios = FXCollections.observableArrayList(funcionarioDAO.search(termo));
                tableFuncionarios.setItems(funcionarios);
            }
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Pesquisar Funcionários", "Não foi possível realizar a pesquisa.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Main.go(fxmlPath, title);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Navegação", "Não foi possível carregar a tela.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


