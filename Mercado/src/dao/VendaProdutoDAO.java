package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectionFactory.ConnectionDatabase;
import model.VendaProduto;

public class VendaProdutoDAO {

    // CREATE
    public void create(VendaProduto vendaProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "INSERT INTO VendaProduto (idVenda, idProduto, quantidade, valorTotal) " +
                "VALUES (?, ?, ?, ?)");
            
            stmt.setInt(1, vendaProduto.getIdVenda());
            stmt.setInt(2, vendaProduto.getIdProduto());
            stmt.setInt(3, vendaProduto.getQuantidade());
            stmt.setBigDecimal(4, vendaProduto.getValorTotal());
            
            stmt.executeUpdate();
            System.out.println("Item de venda cadastrado com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar item de venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // READ BY VENDA (todos os itens de uma venda)
    public List<VendaProduto> readByVenda(int idVenda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<VendaProduto> itens = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM VendaProduto WHERE idVenda = ?");
            stmt.setInt(1, idVenda);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                VendaProduto item = new VendaProduto();
                item.setIdVendaProduto(rs.getInt("idVendaProduto"));
                item.setIdVenda(rs.getInt("idVenda"));
                item.setIdProduto(rs.getInt("idProduto"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setValorTotal(rs.getBigDecimal("valorTotal"));
                
                itens.add(item);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler itens da venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return itens;
    }

    // READ BY PRODUTO (todas as vendas de um produto)
    public List<VendaProduto> readByProduto(int idProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<VendaProduto> itens = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM VendaProduto WHERE idProduto = ?");
            stmt.setInt(1, idProduto);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                VendaProduto item = new VendaProduto();
                item.setIdVendaProduto(rs.getInt("idVendaProduto"));
                item.setIdVenda(rs.getInt("idVenda"));
                item.setIdProduto(rs.getInt("idProduto"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setValorTotal(rs.getBigDecimal("valorTotal"));
                
                itens.add(item);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler vendas do produto", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return itens;
    }

    // UPDATE
    public void update(VendaProduto vendaProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "UPDATE VendaProduto SET idVenda=?, idProduto=?, quantidade=?, valorTotal=? " +
                "WHERE idVendaProduto=?");
            
            stmt.setInt(1, vendaProduto.getIdVenda());
            stmt.setInt(2, vendaProduto.getIdProduto());
            stmt.setInt(3, vendaProduto.getQuantidade());
            stmt.setBigDecimal(4, vendaProduto.getValorTotal());
            stmt.setInt(5, vendaProduto.getIdVendaProduto());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Item de venda atualizado com sucesso!");
            } else {
                System.out.println("Nenhum item de venda encontrado com o ID: " + vendaProduto.getIdVendaProduto());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item de venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // DELETE
    public void delete(int idVendaProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM VendaProduto WHERE idVendaProduto = ?");
            stmt.setInt(1, idVendaProduto);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Item de venda excluído com sucesso!");
            } else {
                System.out.println("Nenhum item de venda encontrado com o ID: " + idVendaProduto);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item de venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // DELETE BY VENDA (excluir todos os itens de uma venda)
    public void deleteByVenda(int idVenda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM VendaProduto WHERE idVenda = ?");
            stmt.setInt(1, idVenda);
            
            int affectedRows = stmt.executeUpdate();
            System.out.println(affectedRows + " itens de venda excluídos para a venda ID: " + idVenda);
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir itens da venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // CALCULAR TOTAL DA VENDA
    public BigDecimal calcularTotalVenda(int idVenda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BigDecimal total = BigDecimal.ZERO;
        
        try {
            stmt = con.prepareStatement("SELECT SUM(valorTotal) AS total FROM VendaProduto WHERE idVenda = ?");
            stmt.setInt(1, idVenda);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                total = rs.getBigDecimal("total");
                if (total == null) {
                    total = BigDecimal.ZERO;
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total da venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return total;
    }

    // CALCULAR QUANTIDADE TOTAL DA VENDA
    public int calcularQuantidadeTotalVenda(int idVenda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int quantidadeTotal = 0;
        
        try {
            stmt = con.prepareStatement("SELECT SUM(quantidade) AS total FROM VendaProduto WHERE idVenda = ?");
            stmt.setInt(1, idVenda);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                quantidadeTotal = rs.getInt("total");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular quantidade total da venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return quantidadeTotal;
    }

    // VERIFICAR SE PRODUTO FOI VENDIDO
    public boolean produtoFoiVendido(int idProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean foiVendido = false;
        
        try {
            stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM VendaProduto WHERE idProduto = ?");
            stmt.setInt(1, idProduto);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                foiVendido = rs.getInt("total") > 0;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar vendas do produto", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return foiVendido;
    }
}