package dao;

import connectionFactory.ConnectionDatabase;
import model.VendaProduto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaProdutoDAO {

    public boolean create(VendaProduto vp) {
        String sql = "INSERT INTO VendaProduto (idVenda, idProduto, quantidade, valorTotal) VALUES (?,?,?,?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, vp.getIdVenda());
            stmt.setInt(2, vp.getIdProduto());
            stmt.setInt(3, vp.getQuantidade());
            stmt.setBigDecimal(4, nvl(vp.getValorTotal()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar item de venda", e);
        }
    }

    public int createMany(int idVenda, List<VendaProduto> itens) {
        if (itens == null || itens.isEmpty()) return 0;
        String sql = "INSERT INTO VendaProduto (idVenda, idProduto, quantidade, valorTotal) VALUES (?,?,?,?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            for (VendaProduto vp : itens) {
                stmt.setInt(1, idVenda);
                stmt.setInt(2, vp.getIdProduto());
                stmt.setInt(3, vp.getQuantidade());
                stmt.setBigDecimal(4, nvl(vp.getValorTotal()));
                stmt.addBatch();
            }
            int[] res = stmt.executeBatch();
            int ok = 0; for (int r : res) ok += (r >= 0 ? 1 : 0);
            return ok;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar itens da venda", e);
        }
    }

    public boolean produtoFoiVendido(int idProduto) {
        String sql = "SELECT TOP 1 1 FROM VendaProduto WHERE idProduto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar vendas do produto", e);
        }
    }

    // ...demais métodos iguais ao que você já enviou...

    private BigDecimal nvl(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }
}
