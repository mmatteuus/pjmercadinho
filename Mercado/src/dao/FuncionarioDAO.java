package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Funcionario;

public class FuncionarioDAO {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Cadastrar
    public void create(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (nomeFuncionario, cpfFuncionario, dataNasc, telefone, endereco, email, cargo, nivel, senha) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getCpfFuncionario());
            stmt.setDate(3, convertStringToSqlDate(funcionario.getDataNasc()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getEmail());
            stmt.setString(7, funcionario.getCargo());
            stmt.setString(8, funcionario.getNivel());
            stmt.setString(9, funcionario.getSenha());

            stmt.executeUpdate();
            System.out.println("✅ Funcionário cadastrado com sucesso.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar funcionário!", e);
        }
    }

    // Listar todos
    public ArrayList<Funcionario> read() {
        ArrayList<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";

        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(extrairFuncionario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler funcionários!", e);
        }

        return lista;
    }

    // Atualizar
    public void update(Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET nomeFuncionario=?, cpfFuncionario=?, dataNasc=?, telefone=?, endereco=?, email=?, cargo=?, nivel=?, senha=? WHERE idFuncionario=?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getCpfFuncionario());
            stmt.setDate(3, convertStringToSqlDate(funcionario.getDataNasc()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getEmail());
            stmt.setString(7, funcionario.getCargo());
            stmt.setString(8, funcionario.getNivel());
            stmt.setString(9, funcionario.getSenha());
            stmt.setString(10, funcionario.getIdFuncionario());

            stmt.executeUpdate();
            System.out.println("✅ Funcionário atualizado com sucesso.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário!", e);
        }
    }

    // Deletar
    public void delete(String idFuncionario) {
        String sql = "DELETE FROM Funcionario WHERE idFuncionario=?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, idFuncionario);
            stmt.executeUpdate();
            System.out.println("✅ Funcionário removido com sucesso.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover funcionário!", e);
        }
    }

    // Pesquisar por nome ou CPF
    public ArrayList<Funcionario> search(String termo) {
        ArrayList<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario WHERE nomeFuncionario LIKE ? OR cpfFuncionario LIKE ?";
        String filtro = "%" + termo + "%";

        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, filtro);
            stmt.setString(2, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(extrairFuncionario(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar funcionários!", e);
        }

        return lista;
    }

    // Buscar por ID
    public Funcionario getById(String idFuncionario) {
        String sql = "SELECT * FROM Funcionario WHERE idFuncionario=?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, idFuncionario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairFuncionario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário!", e);
        }

        return null;
    }

    // Autenticar login
    public Funcionario autenticarUser(String cpf, String senha) {
        String sql = "SELECT * FROM Funcionario WHERE cpfFuncionario=? AND senha=?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairFuncionario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário!", e);
        }

        return null;
    }

    // ========== UTILITÁRIOS ==========

    private Funcionario extrairFuncionario(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getString("idFuncionario"));
        f.setNomeFuncionario(rs.getString("nomeFuncionario"));
        f.setCpfFuncionario(rs.getString("cpfFuncionario"));
        f.setDataNasc(convertSqlDateToString(rs.getDate("dataNasc")));
        f.setTelefone(rs.getString("telefone"));
        f.setEndereco(rs.getString("endereco"));
        f.setEmail(rs.getString("email"));
        f.setCargo(rs.getString("cargo"));
        f.setNivel(rs.getString("nivel"));
        f.setSenha(rs.getString("senha"));
        return f;
    }

    private java.sql.Date convertStringToSqlDate(String data) {
        if (data == null || data.isEmpty()) return null;
        try {
            LocalDate localDate = LocalDate.parse(data, DATE_FORMATTER);
            return java.sql.Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Data inválida (use dd/MM/yyyy): " + data, e);
        }
    }

    private String convertSqlDateToString(java.sql.Date date) {
        return (date == null) ? null : date.toLocalDate().format(DATE_FORMATTER);
    }
}
