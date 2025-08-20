package controller;

import application.Main;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Funcionario;
import model.Produto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ControllerRelatorioProdutos {
    @FXML private Label lblUser;
    @FXML private TextField txtBusca;
    @FXML private Button btnPesquisar;
    @FXML private TableView<Produto> tableProdutos;
    @FXML private TableColumn<Produto, Number>  colCodigo;    // idProduto
    @FXML private TableColumn<Produto, String>  colNome;      // nomeProduto
    @FXML private TableColumn<Produto, String>  colCategoria; // tipoUn (ou crie campo categoria na tabela/model se desejar)
    @FXML private TableColumn<Produto, Number>  colPreco;     // precoUn
    @FXML private TableColumn<Produto, Number>  colEstoque;   // estoque

    // Botões de navegação (adicionados para consistência com outros controllers)
    @FXML private Button btnRegistrarVenda, btnMenuPrincipal, btnClientes, btnProdutos, btnVendas, btnFuncionarios, btnLogout;

    private final ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final NumberFormat brMoney = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

    @FXML
    public void initialize() {
        // Mostra nome do usuário logado
        Funcionario usuarioLogado = Main.usuarioLogado;
        if (usuarioLogado != null && lblUser != null) {
            lblUser.setText(usuarioLogado.getNomeFuncionario());
        }

        configurarTabela();
        carregarProdutos();
        txtBusca.setOnAction(this::onPesquisar);
        btnPesquisar.setOnAction(this::onPesquisar);

        // Configuração dos botões de navegação
        btnRegistrarVenda.setOnAction(e -> navigateTo("/view/viewCadastroVenda.fxml", "Mercadinho - Registrar Venda"));
        btnMenuPrincipal.setOnAction(e -> navigateTo("/view/viewMainMenu.fxml", "Mercadinho - Menu Principal"));
        btnClientes.setOnAction(e -> navigateTo("/view/viewCadrastroCliente.fxml", "Mercadinho - Clientes"));
        btnProdutos.setOnAction(e -> navigateTo("/view/viewRelatorioProdutos.fxml", "Mercadinho - Produtos"));
        btnVendas.setOnAction(e -> navigateTo("/view/viewRelatorioVenda.fxml", "Mercadinho - Vendas"));
        btnFuncionarios.setOnAction(e -> navigateTo("/view/viewRelatorioFuncionario.fxml", "Mercadinho - Funcionários"));
        btnLogout.setOnAction(e -> Main.TelaLogin());

        // Visibilidade do botão de funcionários (se aplicável)
        if (usuarioLogado != null && !"ADMIN".equalsIgnoreCase(usuarioLogado.getNivel())) {
            btnFuncionarios.setVisible(false);
        }
    }

    private void configurarTabela() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("tipoUn"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoUn"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        colPreco.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : brMoney.format(value.doubleValue()));
            }
        });

        tableProdutos.setPlaceholder(new Label("Nenhum produto encontrado."));
        tableProdutos.setItems(listaProdutos);
    }

    private void carregarProdutos() {
        try {
            List<Produto> produtos = produtoDAO.read();
            listaProdutos.setAll(produtos);
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Produtos", "Não foi possível carregar a lista de produtos.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onPesquisar(ActionEvent event) {
        String termo = txtBusca.getText() == null ? "" : txtBusca.getText().trim();
        try {
            if (termo.isEmpty()) {
                carregarProdutos();
            } else {
                List<Produto> resultados = produtoDAO.search(termo);
                listaProdutos.setAll(resultados);
            }
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Pesquisar Produtos", "Não foi possível realizar a pesquisa.\nDetalhes: " + e.getMessage());
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


