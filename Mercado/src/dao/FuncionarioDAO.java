package dao;

import connectionFactory.ConnectionDatabase;
import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public Funcionario autenticarUser(String cpf, String senha) {
        String sql = "SELECT TOP 1 * FROM Funcionario WHERE cpfFuncionario = ? AND senha = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }

    public List<Funcionario> read() {
        String sql = "SELECT * FROM Funcionario ORDER BY nomeFuncionario";
        List<Funcionario> list = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários", e);
        }
    }

    public List<Funcionario> readVendedores() {
        String sql = "SELECT * FROM Funcionario WHERE UPPER(cargo)='VENDEDOR' OR UPPER(nivel)='VENDEDOR' ORDER BY nomeFuncionario";
        List<Funcionario> list = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendedores", e);
        }
    }

    public String readTotalVendido(String cpfFuncionario) {
        String sql = """
            SELECT COALESCE(SUM(v.precoTotal),0) AS total
            FROM Venda v
            JOIN Funcionario f ON f.idFuncionario = v.idFuncionario
            WHERE f.cpfFuncionario = ?
        """;
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpfFuncionario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBigDecimal(1).toPlainString();
                return "0";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total vendido", e);
        }
    }

    private Funcionario map(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(String.valueOf(rs.getInt("idFuncionario")));
        f.setNomeFuncionario(rs.getString("nomeFuncionario"));
        f.setCpfFuncionario(rs.getString("cpfFuncionario"));
        f.setDataNasc(rs.getString("dataNasc"));
        f.setTelefone(rs.getString("telefone"));
        f.setEndereco(rs.getString("endereco"));
        f.setEmail(rs.getString("email"));
        f.setCargo(rs.getString("cargo"));
        f.setNivel(rs.getString("nivel"));
        f.setSenha(rs.getString("senha"));
        return f;
    }
}
