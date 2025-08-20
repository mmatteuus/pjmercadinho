package controller;

import application.Main;
import dao.VendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Funcionario;
import model.Venda;
import javafx.event.ActionEvent;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ControllerRelatorioVenda {

    @FXML private Label lblUser;
    @FXML private TextField txtBusca;
    @FXML private Button btnPesquisar;

    @FXML private TableView<Venda> tableVendas;
    @FXML private TableColumn<Venda,Integer> colId, colVendedor, colCliente;
    @FXML private TableColumn<Venda, LocalDate> colData;
    @FXML private TableColumn<Venda, Number> colTotal;

    // Botões de navegação (adicionados para consistência com outros controllers)
    @FXML private Button btnRegistrarVenda, btnMenuPrincipal, btnClientes, btnProdutos, btnVendas, btnFuncionarios, btnLogout;

    private final ObservableList<Venda> vendas = FXCollections.observableArrayList();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final NumberFormat brMoney = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

    @FXML
    public void initialize() {
        // Mostra nome do usuário logado
        Funcionario usuarioLogado = Main.usuarioLogado;
        if (usuarioLogado != null && lblUser != null) {
            lblUser.setText(usuarioLogado.getNomeFuncionario());
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        colVendedor.setCellValueFactory(new PropertyValueFactory<>("idFuncionario")); // Pode ser melhorado para mostrar o nome do vendedor
        colCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));     // Pode ser melhorado para mostrar o nome do cliente
        colData.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));

        colTotal.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Number v, boolean empty) {
                super.updateItem(v, empty);
                setText(empty || v == null ? null : brMoney.format(v.doubleValue()));
            }
        });

        tableVendas.setItems(vendas);
        carregarVendas();

        txtBusca.setOnAction(e -> onPesquisar());
        btnPesquisar.setOnAction(e -> onPesquisar());

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

    private void carregarVendas() {
        try {
            List<Venda> listaVendas = vendaDAO.read();
            vendas.setAll(listaVendas);
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Vendas", "Não foi possível carregar a lista de vendas.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onPesquisar() {
        String termo = txtBusca.getText() == null ? "" : txtBusca.getText().trim().toLowerCase();
        try {
            if (termo.isEmpty()) {
                carregarVendas(); // Recarrega todas as vendas se a busca estiver vazia
            } else {
                // Filtra a lista existente em vez de ir ao banco novamente para buscas simples
                FilteredList<Venda> filtro = new FilteredList<>(vendas, v -> {
                    String id = String.valueOf(v.getIdVenda());
                    String vend = String.valueOf(v.getIdFuncionario()); // Ou v.getVendedorNome() se o model for atualizado
                    String cli = String.valueOf(v.getIdCliente());     // Ou v.getClienteNome() se o model for atualizado
                    String data = v.getDataVenda() == null ? "" : v.getDataVenda().toString();
                    String tot = v.getPrecoTotal() == null ? "" : brMoney.format(v.getPrecoTotal());
                    return id.contains(termo) || vend.contains(termo) || cli.contains(termo) || data.contains(termo) || tot.toLowerCase().contains(termo);
                });
                SortedList<Venda> ord = new SortedList<>(filtro);
                ord.comparatorProperty().bind(tableVendas.comparatorProperty());
                tableVendas.setItems(ord);
            }
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Pesquisar Vendas", "Não foi possível realizar a pesquisa.\nDetalhes: " + e.getMessage());
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


