package dao;

import connectionFactory.ConnectionDatabase;
import model.Produto;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean create(Produto p) {
        String sql = "INSERT INTO Produto (nomeProduto, codBarra, tipoUn, dataFab, dataVal, precoUn, estoque) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNomeProduto());
            stmt.setString(2, p.getCodBarra());
            stmt.setString(3, p.getTipoUn());
            stmt.setDate(4, toSqlDate(p.getDataFab()));
            stmt.setDate(5, toSqlDate(p.getDataVal()));
            stmt.setBigDecimal(6, nvl(p.getPrecoUn()));
            stmt.setInt(7, p.getEstoque());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar produto", e);
        }
    }

    public List<Produto> read() {
        String sql = "SELECT * FROM Produto";
        List<Produto> list = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler produtos", e);
        }
        return list;
    }

    public Produto readById(int idProduto) {
        String sql = "SELECT * FROM Produto WHERE idProduto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID", e);
        }
    }

    public List<Produto> search(String termo) {
        String like = "%" + termo + "%";
        String sql = "SELECT * FROM Produto WHERE nomeProduto LIKE ? OR codBarra LIKE ?";
        List<Produto> list = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, like);
            stmt.setString(2, like);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos", e);
        }
        return list;
    }

    public boolean update(Produto p) {
        String sql = "UPDATE Produto SET nomeProduto=?, codBarra=?, tipoUn=?, dataFab=?, dataVal=?, precoUn=?, estoque=? WHERE idProduto=?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNomeProduto());
            stmt.setString(2, p.getCodBarra());
            stmt.setString(3, p.getTipoUn());
            stmt.setDate(4, toSqlDate(p.getDataFab()));
            stmt.setDate(5, toSqlDate(p.getDataVal()));
            stmt.setBigDecimal(6, nvl(p.getPrecoUn()));
            stmt.setInt(7, p.getEstoque());
            stmt.setInt(8, p.getIdProduto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }

    public boolean delete(int idProduto) {
        String sql = "DELETE FROM Produto WHERE idProduto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto", e);
        }
    }

    public boolean atualizarEstoque(int idProduto, int delta) {
        String sql = "UPDATE Produto SET estoque = estoque + ? WHERE idProduto = ? AND estoque + ? >= 0";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, delta);
            stmt.setInt(2, idProduto);
            stmt.setInt(3, delta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estoque", e);
        }
    }

    public List<Produto> readProdutoEB() { return readProdutoEB(5); }

    public List<Produto> readProdutoEB(int threshold) {
        String sql = "SELECT * FROM Produto WHERE estoque <= ? ORDER BY estoque ASC";
        List<Produto> list = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos com estoque baixo", e);
        }
        return list;
    }

    public List<Produto> readProdutoAV() { return readProdutoAV(30); }

    public List<Produto> readProdutoAV(int dias) {
        String sql = """
            SELECT * FROM Produto
            WHERE dataVal IS NOT NULL
              AND CAST(dataVal AS date) >= CAST(GETDATE() AS date)
              AND CAST(dataVal AS date) <= DATEADD(day, ?, CAST(GETDATE() AS date))
            ORDER BY dataVal ASC
        """;
        List<Produto> list = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, dias);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos a vencer", e);
        }
        return list;
    }

    private Produto mapRow(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setIdProduto(rs.getInt("idProduto"));
        p.setNomeProduto(rs.getString("nomeProduto"));
        p.setCodBarra(rs.getString("codBarra"));
        p.setTipoUn(rs.getString("tipoUn"));
        p.setDataFab(toLocalDate(rs.getDate("dataFab")));
        p.setDataVal(toLocalDate(rs.getDate("dataVal")));
        p.setPrecoUn(rs.getBigDecimal("precoUn"));
        p.setEstoque(rs.getInt("estoque"));
        return p;
    }

    private Date toSqlDate(LocalDate d) { return d == null ? null : Date.valueOf(d); }
    private LocalDate toLocalDate(Date d) { return d == null ? null : d.toLocalDate(); }
    private BigDecimal nvl(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }
}


