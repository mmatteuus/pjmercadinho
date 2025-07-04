package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectionFactory.ConnectionDatabase;
import model.Venda;

public class VendaDAO {

    // CREATE
    public int create(Venda venda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int idVendaGerada = -1;
        
        try {
            stmt = con.prepareStatement(
                "INSERT INTO Venda (idCliente, idFuncionario, dataVenda, precoTotal, formaPag, quantTotal) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, venda.getIdCliente());
            stmt.setInt(2, venda.getIdFuncionario());
            stmt.setDate(3, java.sql.Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(4, venda.getPrecoTotal());
            stmt.setString(5, venda.getFormaPag());
            stmt.setInt(6, venda.getQuantTotal());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar venda, nenhuma linha afetada.");
            }
            
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idVendaGerada = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID gerado para a venda.");
            }
            
            System.out.println("Venda cadastrada com sucesso! ID: " + idVendaGerada);
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, generatedKeys);
        }
        
        return idVendaGerada;
    }

    // READ ALL
    public List<Venda> readAll() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Venda");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setIdCliente(rs.getInt("idCliente"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
                venda.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                venda.setPrecoTotal(rs.getBigDecimal("precoTotal"));
                venda.setFormaPag(rs.getString("formaPag"));
                venda.setQuantTotal(rs.getInt("quantTotal"));
                
                vendas.add(venda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler vendas", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return vendas;
    }

    // READ BY ID
    public Venda readById(int idVenda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Venda venda = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Venda WHERE idVenda = ?");
            stmt.setInt(1, idVenda);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setIdCliente(rs.getInt("idCliente"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
                venda.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                venda.setPrecoTotal(rs.getBigDecimal("precoTotal"));
                venda.setFormaPag(rs.getString("formaPag"));
                venda.setQuantTotal(rs.getInt("quantTotal"));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda por ID", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return venda;
    }

    // UPDATE
    public void update(Venda venda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "UPDATE Venda SET idCliente=?, idFuncionario=?, dataVenda=?, " +
                "precoTotal=?, formaPag=?, quantTotal=? WHERE idVenda=?");
            
            stmt.setInt(1, venda.getIdCliente());
            stmt.setInt(2, venda.getIdFuncionario());
            stmt.setDate(3, java.sql.Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(4, venda.getPrecoTotal());
            stmt.setString(5, venda.getFormaPag());
            stmt.setInt(6, venda.getQuantTotal());
            stmt.setInt(7, venda.getIdVenda());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Venda atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma venda encontrada com o ID: " + venda.getIdVenda());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // DELETE
    public void delete(int idVenda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM Venda WHERE idVenda = ?");
            stmt.setInt(1, idVenda);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Venda excluída com sucesso!");
            } else {
                System.out.println("Nenhuma venda encontrada com o ID: " + idVenda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    // BUSCA POR DATA
    public List<Venda> buscarPorData(LocalDate dataInicio, LocalDate dataFim) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Venda WHERE dataVenda BETWEEN ? AND ?");
            stmt.setDate(1, java.sql.Date.valueOf(dataInicio));
            stmt.setDate(2, java.sql.Date.valueOf(dataFim));
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setIdCliente(rs.getInt("idCliente"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
                venda.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                venda.setPrecoTotal(rs.getBigDecimal("precoTotal"));
                venda.setFormaPag(rs.getString("formaPag"));
                venda.setQuantTotal(rs.getInt("quantTotal"));
                
                vendas.add(venda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas por data", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return vendas;
    }

    // BUSCA POR CLIENTE
    public List<Venda> buscarPorCliente(int idCliente) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Venda WHERE idCliente = ?");
            stmt.setInt(1, idCliente);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setIdCliente(rs.getInt("idCliente"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
                venda.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                venda.setPrecoTotal(rs.getBigDecimal("precoTotal"));
                venda.setFormaPag(rs.getString("formaPag"));
                venda.setQuantTotal(rs.getInt("quantTotal"));
                
                vendas.add(venda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas por cliente", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return vendas;
    }

    // BUSCA POR FUNCIONÁRIO
    public List<Venda> buscarPorFuncionario(int idFuncionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Venda WHERE idFuncionario = ?");
            stmt.setInt(1, idFuncionario);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setIdCliente(rs.getInt("idCliente"));
                venda.setIdFuncionario(rs.getInt("idFuncionario"));
                venda.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                venda.setPrecoTotal(rs.getBigDecimal("precoTotal"));
                venda.setFormaPag(rs.getString("formaPag"));
                venda.setQuantTotal(rs.getInt("quantTotal"));
                
                vendas.add(venda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas por funcionário", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return vendas;
    }

    // ATUALIZAR TOTAL DA VENDA
    public void atualizarTotalVenda(int idVenda, BigDecimal novoTotal, int novaQuantidade) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(
                "UPDATE Venda SET precoTotal = ?, quantTotal = ? WHERE idVenda = ?");
            stmt.setBigDecimal(1, novoTotal);
            stmt.setInt(2, novaQuantidade);
            stmt.setInt(3, idVenda);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Total da venda atualizado com sucesso!");
            } else {
                System.out.println("Nenhuma venda encontrada com o ID: " + idVenda);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar total da venda", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
}