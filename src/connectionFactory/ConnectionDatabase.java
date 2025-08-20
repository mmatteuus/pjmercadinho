package connectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectionDatabase {

    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de configuração: " + e.getMessage());
            throw new RuntimeException("Erro ao carregar configurações do banco de dados.", e);
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName(props.getProperty("db.driver"));
            System.out.println("Conexão realizada!");
            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
            throw new RuntimeException("Erro de conexão com o banco de dados!", e);
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println("Conexão fechada!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
            throw new RuntimeException("Erro ao fechar conexão!", e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            throw new RuntimeException("Erro ao fechar PreparedStatement!", e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
            throw new RuntimeException("Erro ao fechar ResultSet!", e);
        }
    }
}


