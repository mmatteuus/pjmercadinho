package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Funcionario;

public class FuncionarioDAO {

    public void create(Funcionario funcionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO Funcionario (nomeFuncionario, cpfFuncionario, dataNasc, telefone, endereco, email, cargo, nivel) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getCpfFuncionario());
            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNasc()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getEmail());
            stmt.setString(7, funcionario.getCargo());
            stmt.setInt(8, funcionario.getNivel());

            stmt.execute();
            System.out.println("Funcionário cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    public ArrayList<Funcionario> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM Funcionario");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                funcionario.setCpfFuncionario(rs.getString("cpfFuncionario"));
                funcionario.setDataNasc(rs.getDate("dataNasc"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setNivel(rs.getInt("nivel"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler os dados dos funcionários!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return funcionarios;
    }

    public void update(Funcionario funcionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE Funcionario SET nomeFuncionario=?, cpfFuncionario=?, dataNasc=?, "
                    + "telefone=?, endereco=?, email=?, cargo=?, nivel=? WHERE idFuncionario=?");
            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getCpfFuncionario());
            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNasc()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getEmail());
            stmt.setString(7, funcionario.getCargo());
            stmt.setInt(8, funcionario.getNivel());
            stmt.setInt(9, funcionario.getIdFuncionario());

            stmt.execute();
            System.out.println("Funcionário atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }

    public void delete(int idFuncionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM Funcionario WHERE idFuncionario=?");
            stmt.setInt(1, idFuncionario);

            stmt.execute();
            System.out.println("Funcionário removido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover funcionário", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    public ArrayList<Funcionario> search(String pesquisar) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        pesquisar = "%" + pesquisar + "%";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM Funcionario WHERE nomeFuncionario LIKE ? OR cpfFuncionario LIKE ?");
            stmt.setString(1, pesquisar);
            stmt.setString(2, pesquisar);
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                funcionario.setCpfFuncionario(rs.getString("cpfFuncionario"));
                funcionario.setDataNasc(rs.getDate("dataNasc").toLocalDate());
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setNivel(rs.getInt("nivel"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar funcionários!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return funcionarios;
    }
    
    // Método adicional para buscar por ID
    public Funcionario getById(int idFuncionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Funcionario funcionario = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Funcionario WHERE idFuncionario = ?");
            stmt.setInt(1, idFuncionario);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                funcionario.setCpfFuncionario(rs.getString("cpfFuncionario"));
                funcionario.setDataNasc(rs.getDate("dataNasc").toLocalDate());
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setNivel(rs.getInt("nivel"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário por ID", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return funcionario;
    }
}