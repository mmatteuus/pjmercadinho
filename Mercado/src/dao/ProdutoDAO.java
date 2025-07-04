package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectionFactory.ConnectionDatabase;
import model.Produto;

public class ProdutoDAO {

    // CREATE
    public void create(Produto produto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "INSERT INTO Produto (nomeProduto, codBarra, tipoUn, dataFab, dataVal, precoUn, estoque) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getCodBarra());
            stmt.setString(3, produto.getTipoUn());
            stmt.setDate(4, java.sql.Date.valueOf(produto.getDataFab()));
            stmt.setDate(5, produto.getDataVal() != null ? java.sql.Date.valueOf(produto.getDataVal()) : null);
            stmt.setBigDecimal(6, produto.getPrecoUn());
            stmt.setInt(7, produto.getEstoque());
            
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar produto", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // READ ALL
    public List<Produto> readAll() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Produto");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNomeProduto(rs.getString("nomeProduto"));
                produto.setCodBarra(rs.getString("codBarra"));
                produto.setTipoUn(rs.getString("tipoUn"));
                produto.setDataFab(rs.getDate("dataFab").toLocalDate());
                
                java.sql.Date dataVal = rs.getDate("dataVal");
                produto.setDataVal(dataVal != null ? dataVal.toLocalDate() : null);
                
                produto.setPrecoUn(rs.getBigDecimal("precoUn"));
                produto.setEstoque(rs.getInt("estoque"));
                
                produtos.add(produto);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler produtos", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return produtos;
    }

    // UPDATE
    public void update(Produto produto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "UPDATE Produto SET nomeProduto=?, codBarra=?, tipoUn=?, " +
                "dataFab=?, dataVal=?, precoUn=?, estoque=? WHERE idProduto=?");
            
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getCodBarra());
            stmt.setString(3, produto.getTipoUn());
            stmt.setDate(4, java.sql.Date.valueOf(produto.getDataFab()));
            stmt.setDate(5, produto.getDataVal() != null ? java.sql.Date.valueOf(produto.getDataVal()) : null);
            stmt.setBigDecimal(6, produto.getPrecoUn());
            stmt.setInt(7, produto.getEstoque());
            stmt.setInt(8, produto.getIdProduto());
            
            stmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // DELETE
    public void delete(int idProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM Produto WHERE idProduto = ?");
            stmt.setInt(1, idProduto);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Produto excluído com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID: " + idProduto);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // READ BY ID
    public Produto readById(int idProduto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto produto = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Produto WHERE idProduto = ?");
            stmt.setInt(1, idProduto);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNomeProduto(rs.getString("nomeProduto"));
                produto.setCodBarra(rs.getString("codBarra"));
                produto.setTipoUn(rs.getString("tipoUn"));
                produto.setDataFab(rs.getDate("dataFab").toLocalDate());
                
                java.sql.Date dataVal = rs.getDate("dataVal");
                produto.setDataVal(dataVal != null ? dataVal.toLocalDate() : null);
                
                produto.setPrecoUn(rs.getBigDecimal("precoUn"));
                produto.setEstoque(rs.getInt("estoque"));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return produto;
    }

    // BUSCA POR NOME OU CÓDIGO DE BARRAS
    public List<Produto> search(String termo) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement(
                "SELECT * FROM Produto WHERE nomeProduto LIKE ? OR codBarra LIKE ?");
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNomeProduto(rs.getString("nomeProduto"));
                produto.setCodBarra(rs.getString("codBarra"));
                produto.setTipoUn(rs.getString("tipoUn"));
                produto.setDataFab(rs.getDate("dataFab").toLocalDate());
                
                java.sql.Date dataVal = rs.getDate("dataVal");
                produto.setDataVal(dataVal != null ? dataVal.toLocalDate() : null);
                
                produto.setPrecoUn(rs.getBigDecimal("precoUn"));
                produto.setEstoque(rs.getInt("estoque"));
                
                produtos.add(produto);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return produtos;
    }

    // ATUALIZAR ESTOQUE
    public void atualizarEstoque(int idProduto, int quantidade) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "UPDATE Produto SET estoque = estoque + ? WHERE idProduto = ?");
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idProduto);
            
            stmt.executeUpdate();
            System.out.println("Estoque atualizado com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estoque", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
}