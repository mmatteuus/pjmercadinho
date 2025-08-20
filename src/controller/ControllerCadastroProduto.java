package controller;

import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class controllerCadastroProduto {

    // Menu lateral
    @FXML private Button btnRegistrarVenda, btnMenuPrincipal, btnClientes, btnProdutos, btnVendas, btnFuncionarios, btnLogout;
    @FXML private Label  lblUser;

    // Formulário
    @FXML private TextField txtNome, txtCodigoBarras, txtPrecoUnitario, txtTipoUnidade, txtEstoque;
    @FXML private DatePicker dpFabricacao, dpValidade;
    @FXML private Button btnSalvar, btnCancelar;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @FXML
    public void initialize() {
        lblUser.setText(controllerLogin.funcionario != null ? controllerLogin.funcionario.getNomeFuncionario() : "Usuário");

        txtEstoque.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.isContentChange()) return c;
            String t = c.getControlNewText().replaceAll("\\D","");
            return t.equals(c.getControlNewText()) ? c : null;
        }));

        btnSalvar.setOnAction(e -> salvar());
        btnCancelar.setOnAction(e -> limpar());
    }

    private void salvar() {
        if (vazio(txtNome) || vazio(txtCodigoBarras)) { warn("Informe nome e código de barras."); return; }

        Produto p = new Produto();
        p.setNomeProduto(txtNome.getText().trim());
        p.setCodBarra(txtCodigoBarras.getText().trim());
        p.setTipoUn(n(txtTipoUnidade.getText()));
        p.setDataFab(dpFabricacao.getValue());
        p.setDataVal(dpValidade.getValue());
        p.setPrecoUn(parseMoney(txtPrecoUnitario.getText()));
        p.setEstoque(parseInt(txtEstoque.getText()));

        try {
            boolean ok = produtoDAO.create(p);
            if (ok) { info("Sucesso","Produto cadastrado."); limpar(); }
            else { error("Falha ao salvar produto."); }
        } catch (Exception ex) {
            ex.printStackTrace();
            error("Erro ao salvar produto.");
        }
    }

    private void limpar() {
        txtNome.clear(); txtCodigoBarras.clear(); txtPrecoUnitario.clear(); txtTipoUnidade.clear(); txtEstoque.clear();
        dpFabricacao.setValue((LocalDate) null); dpValidade.setValue((LocalDate) null);
    }

    private BigDecimal parseMoney(String s){
        if (s == null || s.isBlank()) return BigDecimal.ZERO;
        String norm = s.replace(".", "").replace(",", ".");
        try { return new BigDecimal(norm); } catch(Exception e){ return BigDecimal.ZERO; }
    }
    private int parseInt(String s){ try{ return Integer.parseInt(s); }catch(Exception e){ return 0; } }
    private boolean vazio(TextField t){ return t.getText()==null || t.getText().trim().isEmpty(); }
    private String n(String s){ return s==null?"":s.trim(); }
    private void info(String h,String c){ alert(Alert.AlertType.INFORMATION,h,c); }
    private void warn(String c){ alert(Alert.AlertType.WARNING,"Atenção",c); }
    private void error(String c){ alert(Alert.AlertType.ERROR,"Erro",c); }
    private void alert(Alert.AlertType t,String h,String c){ Alert a=new Alert(t); a.setTitle("Sistema"); a.setHeaderText(h); a.setContentText(c); a.showAndWait(); }
}
