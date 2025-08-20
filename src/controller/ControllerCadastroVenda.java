package controller;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import dao.VendaProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Cliente;
import model.Funcionario;
import model.Produto;
import model.Venda;
import model.VendaProduto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ControllerCadastroVenda {

    @FXML private ComboBox<Cliente> cbCli;
    @FXML private ComboBox<Produto> cbProd;
    @FXML private ComboBox<Funcionario> cbVend;
    @FXML private TextField txtCpf;
    @FXML private TextField txtFormaPgto;
    @FXML private TextField txtPrecoUn;
    @FXML private TextField txtTipoUn;
    @FXML private TextField txtQtd;
    @FXML private TextField txtTotal;
    @FXML private TableView<ItemVenda> tableProds;
    @FXML private TableColumn<ItemVenda, String> colProd;
    @FXML private TableColumn<ItemVenda, String> colTipoUn;
    @FXML private TableColumn<ItemVenda, Number> colPrecoUn;
    @FXML private TableColumn<ItemVenda, Integer> colQtd;

    private final ObservableList<ItemVenda> itens = FXCollections.observableArrayList();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();

    @FXML
    private void initialize() {
        configurarTabela();
        configurarCombos();
        carregarDados();
        txtPrecoUn.setEditable(false);
        txtTipoUn.setEditable(false);
        txtTotal.setEditable(false);

        cbProd.setOnAction(e -> preencherCamposProduto());
        cbCli.setOnAction(e -> {
            Cliente c = cbCli.getSelectionModel().getSelectedItem();
            txtCpf.setText(c == null ? "" : c.getCpfCliente());
        });

        txtQtd.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.isContentChange()) return c;
            String t = c.getControlNewText().replaceAll("\\D", "");
            if (t.isEmpty()) return c;
            return t.equals(c.getControlNewText()) ? c : null;
        }));
        atualizarTotal();
    }

    private void configurarTabela() {
        colProd.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colTipoUn.setCellValueFactory(new PropertyValueFactory<>("tipoUn"));
        colPrecoUn.setCellValueFactory(new PropertyValueFactory<>("precoUnit"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tableProds.setItems(itens);
        tableProds.setPlaceholder(new Label("Nenhum item adicionado."));
    }

    private void configurarCombos() {
        cbCli.setConverter(new StringConverter<>() {
            @Override public String toString(Cliente c) { return c == null ? "" : c.getNomeCliente(); }
            @Override public Cliente fromString(String s) { return null; }
        });
        cbProd.setConverter(new StringConverter<>() {
            @Override public String toString(Produto p) { return p == null ? "" : p.getNomeProduto(); }
            @Override public Produto fromString(String s) { return null; }
        });
        cbVend.setConverter(new StringConverter<>() {
            @Override public String toString(Funcionario f) { return f == null ? "" : f.getNomeFuncionario(); }
            @Override public Funcionario fromString(String s) { return null; }
        });
    }

    private void carregarDados() {
        try {
            cbCli.getItems().setAll(clienteDAO.read());
            cbProd.getItems().setAll(produtoDAO.read());
            cbVend.getItems().setAll(funcionarioDAO.readVendedores());
            // if (controllerLogin.funcionario != null) cbVend.getSelectionModel().select(controllerLogin.funcionario);
            // Alterado para Main.usuarioLogado
            if (application.Main.usuarioLogado != null) {
                cbVend.getSelectionModel().select(application.Main.usuarioLogado);
            }
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Dados", "Não foi possível carregar os dados para os campos de seleção.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void preencherCamposProduto() {
        Produto p = cbProd.getSelectionModel().getSelectedItem();
        if (p == null) { txtPrecoUn.clear(); txtTipoUn.clear(); return; }
        txtPrecoUn.setText(String.format(new Locale("pt","BR"), "R$ %,.2f", p.getPrecoUn().doubleValue()));
        txtTipoUn.setText(p.getTipoUn());
    }

    @FXML
    private void onAddProd() {
        Produto p = cbProd.getSelectionModel().getSelectedItem();
        if (p == null) { showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione um produto."); return; }
        int qtd = parseIntSafe(txtQtd.getText());
        if (qtd <= 0) { showAlert(Alert.AlertType.WARNING, "Atenção", "Informe uma quantidade válida."); return; }
        if (qtd > p.getEstoque()) { showAlert(Alert.AlertType.WARNING, "Atenção", "Quantidade acima do estoque disponível."); return; }

        ItemVenda existente = itens.stream().filter(iv -> iv.getIdProduto() == p.getIdProduto()).findFirst().orElse(null);
        if (existente != null) {
            int novaQtd = existente.getQtd() + qtd;
            if (novaQtd > p.getEstoque()) { showAlert(Alert.AlertType.WARNING, "Atenção", "Sem estoque suficiente para adicionar mais itens."); return; }
            existente.setQtd(novaQtd);
            tableProds.refresh();
        } else {
            itens.add(new ItemVenda(p.getIdProduto(), p.getNomeProduto(), p.getTipoUn(), p.getPrecoUn(), qtd));
        }
        txtQtd.clear();
        atualizarTotal();
    }

    @FXML
    private void onExcluirItem() {
        ItemVenda sel = tableProds.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione um item para excluir."); return; }
        itens.remove(sel);
        atualizarTotal();
    }

    @FXML
    private void onSalvar() {
        Cliente cli = cbCli.getSelectionModel().getSelectedItem();
        Funcionario vend = cbVend.getSelectionModel().getSelectedItem();
        String forma = safe(txtFormaPgto.getText());

        if (cli == null) { showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione um cliente."); return; }
        if (vend == null) { showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione um vendedor."); return; }
        if (itens.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Atenção", "Inclua ao menos um item na venda."); return; }
        if (forma.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Atenção", "Informe a forma de pagamento."); return; }

        BigDecimal total = itens.stream().map(ItemVenda::getTotalBD).reduce(BigDecimal.ZERO, BigDecimal::add);
        int quantTotal = itens.stream().mapToInt(ItemVenda::getQtd).sum();

        try {
            // cria venda
            model.Venda v = new model.Venda();
            v.setIdCliente(Integer.parseInt(cli.getIdCliente()));
            v.setIdFuncionario(Integer.parseInt(vend.getIdFuncionario()));
            v.setDataVenda(java.time.LocalDate.now());
            v.setPrecoTotal(total);
            v.setFormaPag(forma);
            v.setQuantTotal(quantTotal);
            int idVenda = vendaDAO.create(v); // seu DAO já retorna id

            // cria itens + baixa estoque
            List<VendaProduto> linhas = new ArrayList<>();
            for (ItemVenda iv : itens) {
                linhas.add(new VendaProduto(idVenda, iv.getIdProduto(), iv.getQtd(), iv.getTotalBD()));
                produtoDAO.atualizarEstoque(iv.getIdProduto(), -iv.getQtd());
            }
            vendaProdutoDAO.createMany(idVenda, linhas);

            showAlert(Alert.AlertType.INFORMATION, "Venda Salva", "Venda registrada com sucesso! Total: " + txtTotal.getText());
            limpar();
        } catch (RuntimeException | SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Salvar Venda", "Não foi possível salvar a venda.\nDetalhes: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void atualizarTotal() {
        BigDecimal total = itens.stream().map(ItemVenda::getTotalBD).reduce(BigDecimal.ZERO, BigDecimal::add);
        txtTotal.setText(String.format(new Locale("pt","BR"), "R$ %,.2f", total.doubleValue()));
    }

    private void limpar() {
        cbCli.getSelectionModel().clearSelection();
        cbProd.getSelectionModel().clearSelection();
        cbVend.getSelectionModel().clearSelection();
        txtCpf.clear(); txtFormaPgto.clear(); txtPrecoUn.clear(); txtQtd.clear(); txtTipoUn.clear();
        itens.clear(); atualizarTotal();
    }

    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class ItemVenda {
        private final int idProduto;
        private final String nomeProduto;
        private final String tipoUn;
        private final BigDecimal precoUnit;
        private int qtd;
        public ItemVenda(int idProduto, String nomeProduto, String tipoUn, BigDecimal precoUnit, int qtd) {
            this.idProduto=idProduto; this.nomeProduto=nomeProduto; this.tipoUn=tipoUn; this.precoUnit=precoUnit; this.qtd=qtd;
        }
        public int getIdProduto(){return idProduto;}
        public String getNomeProduto(){return nomeProduto;}
        public String getTipoUn(){return tipoUn;}
        public BigDecimal getPrecoUnit(){return precoUnit;}
        public int getQtd(){return qtd;}
        public void setQtd(int qtd){this.qtd=qtd;}
        public double getTotal(){return precoUnit.doubleValue()*qtd;}
        public BigDecimal getTotalBD(){return precoUnit.multiply(BigDecimal.valueOf(qtd));}
    }
}


