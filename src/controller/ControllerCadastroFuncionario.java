package controller;

import application.Main;
import dao.FuncionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Funcionario;

import java.util.List;

public class ControllerCadastroFuncionario {

    @FXML private Label lblUser;
    @FXML private TextField txtNome, txtCpf, txtDataNasc, txtCargo, txtEmail, txtTelefone, txtNivel, txtEndereco, txtSenha;
    @FXML private Button btnSalvar, btnCancelar;

    @FXML private TableView<Funcionario> tableFuncionarios;
    @FXML private TableColumn<Funcionario,String> colNome, colCpf, colNascimento, colEmail, colTelefone, colEndereco, colCargo, colNivel;

    private final ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @FXML
    private void initialize() {
        // Mostra nome do usuário logado
        Funcionario usuarioLogado = Main.usuarioLogado;
        if (usuarioLogado != null && lblUser != null) {
            lblUser.setText(usuarioLogado.getNomeFuncionario());
        }

        // Configuração das colunas da tabela
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpfFuncionario"));
        colNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        tableFuncionarios.setItems(listaFuncionarios);

        // Formatação do campo CPF
        txtCpf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText().replaceAll("\\D", "");
            if (newText.length() > 11) {
                newText = newText.substring(0, 11);
            }
            change.setText(newText);
            change.setRange(0, change.getControlText().length());
            return change;
        }));

        carregarFuncionarios();

        btnSalvar.setOnAction(event -> salvarFuncionario());
        btnCancelar.setOnAction(event -> limparCampos());
        tableFuncionarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherCampos(newSelection);
            }
        });
    }

    private void carregarFuncionarios() {
        try {
            List<Funcionario> funcionarios = funcionarioDAO.read();
            listaFuncionarios.setAll(funcionarios);
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Funcionários", "Não foi possível carregar a lista de funcionários.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void preencherCampos(Funcionario funcionario) {
        txtNome.setText(nvl(funcionario.getNomeFuncionario()));
        txtCpf.setText(nvl(funcionario.getCpfFuncionario()));
        txtDataNasc.setText(nvl(funcionario.getDataNasc()));
        txtCargo.setText(nvl(funcionario.getCargo()));
        txtEmail.setText(nvl(funcionario.getEmail()));
        txtTelefone.setText(nvl(funcionario.getTelefone()));
        txtNivel.setText(nvl(funcionario.getNivel()));
        txtEndereco.setText(nvl(funcionario.getEndereco()));
        txtSenha.setText(nvl(funcionario.getSenha()));
    }

    private void salvarFuncionario() {
        if (isCampoVazio(txtNome) || isCampoVazio(txtCpf)) {
            showAlert(Alert.AlertType.WARNING, "Campos Obrigatórios", "Por favor, informe o nome e o CPF do funcionário.");
            return;
        }
        if (!txtCpf.getText().matches("\\d{11}")) {
            showAlert(Alert.AlertType.WARNING, "CPF Inválido", "O CPF deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        Funcionario funcionarioSelecionado = tableFuncionarios.getSelectionModel().getSelectedItem();
        boolean isNovoFuncionario = (funcionarioSelecionado == null);
        Funcionario funcionario = isNovoFuncionario ? new Funcionario() : funcionarioSelecionado;

        funcionario.setNomeFuncionario(txtNome.getText().trim());
        funcionario.setCpfFuncionario(txtCpf.getText().trim());
        funcionario.setDataNasc(nvl(txtDataNasc.getText()));
        funcionario.setCargo(nvl(txtCargo.getText()));
        funcionario.setEmail(nvl(txtEmail.getText()));
        funcionario.setTelefone(nvl(txtTelefone.getText()));
        funcionario.setNivel(nvl(txtNivel.getText()));
        funcionario.setEndereco(nvl(txtEndereco.getText()));
        funcionario.setSenha(nvl(txtSenha.getText()));

        try {
            if (isNovoFuncionario) {
                funcionarioDAO.create(funcionario);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Funcionário cadastrado com sucesso!");
            } else {
                funcionarioDAO.update(funcionario);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Funcionário atualizado com sucesso!");
            }
            carregarFuncionarios();
            limparCampos();
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Salvar Funcionário", "Não foi possível salvar o funcionário.\nDetalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        tableFuncionarios.getSelectionModel().clearSelection();
        txtNome.clear();
        txtCpf.clear();
        txtDataNasc.clear();
        txtCargo.clear();
        txtEmail.clear();
        txtTelefone.clear();
        txtNivel.clear();
        txtEndereco.clear();
        txtSenha.clear();
    }

    // Métodos auxiliares
    private boolean isCampoVazio(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    private String nvl(String s) {
        return s == null ? "" : s.trim();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


