package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Cliente;

public class ClienteDAO {

    public void create(Cliente cliente) {
        String sql = "INSERT INTO cliente (nomeCliente, cpfCliente, dataNasc, telefone, endereco, email) VALUES (?,?,?,?,?,?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getCpfCliente());
            stmt.setString(3, cliente.getDataNasc());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getEmail());

            stmt.execute();
            System.out.println("Cliente cadastrado!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cliente!", e);
        }
    }
    
    public ArrayList<Cliente> read(){
        String sql = "SELECT * FROM Cliente";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getString("idCliente"));
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                cliente.setCpfCliente(rs.getString("cpfCliente"));
                cliente.setDataNasc(rs.getString("dataNasc"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
            
        }catch (SQLException e ) {
            throw new RuntimeException("Erro ao ler os dados dos clientes!",e);
        }
        return clientes;
    }
    
    public void update(Cliente cliente) {
        String sql = "UPDATE Cliente SET nomeCliente = ?, cpfCliente = ?, dataNasc = ?, "
                    + "telefone = ?, endereco = ?, email = ? WHERE cpfCliente = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getCpfCliente());
            stmt.setString(3, cliente.getDataNasc());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getCpfCliente()); // Usado para a cláusula WHERE

            stmt.execute();
            System.out.println("Cliente atualizado!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente!", e);
        }
    }
    
    public void delete(String cpf) {
        String sql = "DELETE FROM Cliente WHERE cpfCliente = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf);
        
            stmt.execute();
            System.out.println("Cliente deletado!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente!", e);
        }
    }
    
    public ArrayList<Cliente> search(String pesquisar){
        String sql = "SELECT * FROM Cliente WHERE nomeCliente LIKE ? OR cpfCliente LIKE ?";
        pesquisar = "%"+ pesquisar + "%";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pesquisar);
            stmt.setString(2, pesquisar);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getString("idCliente"));
                    cliente.setNomeCliente(rs.getString("nomeCliente"));
                    cliente.setCpfCliente(rs.getString("cpfCliente"));
                    cliente.setDataNasc(rs.getString("dataNasc"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setEmail(rs.getString("email"));
                    clientes.add(cliente);
                }
            }
            
        }catch (SQLException e ) {
            throw new RuntimeException("Erro ao pesquisar clientes!",e);
        }
        return clientes;
    }
}


