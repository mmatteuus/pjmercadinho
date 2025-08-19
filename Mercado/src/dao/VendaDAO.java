package dao;

import connectionFactory.ConnectionDatabase;
import model.Venda;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    // CREATE simples: retorna id gerado
    public int create(Venda venda) {
        String sql = "INSERT INTO Venda (idCliente, idFuncionario, dataVenda, precoTotal, formaPag, quantTotal) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, venda.getIdCliente());
            stmt.setInt(2, venda.getIdFuncionario());
            stmt.setDate(3, Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(4, venda.getPrecoTotal());
            stmt.setString(5, venda.getFormaPag());
            stmt.setInt(6, venda.getQuantTotal());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Nenhuma linha afetada no insert de Venda.");

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            throw new SQLException("ID gerado da venda não retornado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar venda", e);
        }
    }

    // READ alias usado nos controllers
    public List<Venda> read() { return readAll(); }

    // READ ALL
    public List<Venda> readAll() {
        String sql = "SELECT * FROM Venda";
        List<Venda> vendas = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) vendas.add(mapRow(rs));
            return vendas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler vendas", e);
        }
    }

    // READ com joins para relatório (clienteNome, vendedorNome)
    public List<Venda> readResumo() {
        String sql = """
            SELECT v.idVenda, v.idCliente, v.idFuncionario, v.dataVenda, v.precoTotal, v.formaPag, v.quantTotal,
                   c.nomeCliente   AS clienteNome,
                   f.nomeFuncionario AS vendedorNome
            FROM Venda v
            JOIN Cliente c     ON c.idCliente = v.idCliente
            JOIN Funcionario f ON f.idFuncionario = v.idFuncionario
            ORDER BY v.dataVenda DESC, v.idVenda DESC
        """;
        List<Venda> vendas = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda v = mapRow(rs);
                // Se o seu model.Venda tiver esses campos:
                try {
                    v.getClass().getMethod("setClienteNome", String.class).invoke(v, rs.getString("clienteNome"));
                    v.getClass().getMethod("setVendedorNome", String.class).invoke(v, rs.getString("vendedorNome"));
                } catch (Exception ignore) { /* campos opcionais */ }
                vendas.add(v);
            }
            return vendas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler resumo de vendas", e);
        }
    }

    // READ BY ID
    public Venda readById(int idVenda) {
        String sql = "SELECT * FROM Venda WHERE idVenda = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda por ID", e);
        }
    }

    // UPDATE
    public boolean update(Venda venda) {
        String sql = "UPDATE Venda SET idCliente=?, idFuncionario=?, dataVenda=?, precoTotal=?, formaPag=?, quantTotal=? WHERE idVenda=?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, venda.getIdCliente());
            stmt.setInt(2, venda.getIdFuncionario());
            stmt.setDate(3, Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(4, venda.getPrecoTotal());
            stmt.setString(5, venda.getFormaPag());
            stmt.setInt(6, venda.getQuantTotal());
            stmt.setInt(7, venda.getIdVenda());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar venda", e);
        }
    }

    // DELETE
    public boolean delete(int idVenda) {
        String sql = "DELETE FROM Venda WHERE idVenda = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir venda", e);
        }
    }

    // BUSCA POR DATA
    public List<Venda> buscarPorData(LocalDate inicio, LocalDate fim) {
        String sql = "SELECT * FROM Venda WHERE dataVenda BETWEEN ? AND ?";
        List<Venda> vendas = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(inicio));
            stmt.setDate(2, Date.valueOf(fim));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) vendas.add(mapRow(rs));
            }
            return vendas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas por data", e);
        }
    }

    // BUSCA POR CLIENTE
    public List<Venda> buscarPorCliente(int idCliente) {
        String sql = "SELECT * FROM Venda WHERE idCliente = ?";
        List<Venda> vendas = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) vendas.add(mapRow(rs));
            }
            return vendas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas por cliente", e);
        }
    }

    // BUSCA POR FUNCIONÁRIO
    public List<Venda> buscarPorFuncionario(int idFuncionario) {
        String sql = "SELECT * FROM Venda WHERE idFuncionario = ?";
        List<Venda> vendas = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) vendas.add(mapRow(rs));
            }
            return vendas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas por funcionário", e);
        }
    }

    // ATUALIZAR TOTAL
    public boolean atualizarTotalVenda(int idVenda, BigDecimal novoTotal, int novaQuantidade) {
        String sql = "UPDATE Venda SET precoTotal = ?, quantTotal = ? WHERE idVenda = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setBigDecimal(1, novoTotal);
            stmt.setInt(2, novaQuantidade);
            stmt.setInt(3, idVenda);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar total da venda", e);
        }
    }

    // ===== Helpers =====

    private Venda mapRow(ResultSet rs) throws SQLException {
        Venda v = new Venda();
        v.setIdVenda(rs.getInt("idVenda"));
        v.setIdCliente(rs.getInt("idCliente"));
        v.setIdFuncionario(rs.getInt("idFuncionario"));
        v.setDataVenda(rs.getDate("dataVenda").toLocalDate());
        v.setPrecoTotal(rs.getBigDecimal("precoTotal"));
        v.setFormaPag(rs.getString("formaPag"));
        v.setQuantTotal(rs.getInt("quantTotal"));
        return v;
    }
}
