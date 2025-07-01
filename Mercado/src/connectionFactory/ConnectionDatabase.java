package connectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionDatabase {
	// Endereço do driver SQL Server
	private final String Driver = "com.microsoft.sqlserver.jdbc.SQLSserverDriver";
	// Endereço do Banco de Dados
	private final String URL = "jdbc:sqlserver://localhost:1433;encrypt=false;databaseName=Mercado";
	// Usuario do Banco de Dados
	private final String user = "sa";
	// Senha do Banco de Dados
	private final String passoword = "Senailab03";

	public Connection getConnection() {

		try {
			Class.forName(Driver);
			System.out.println("Conexão realizada!");
			return DriverManager.getConnection(URL, user, Driver);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Erro de conexão!", e);
		}


	}

	public void closeConnection(Connection con) {
		try {

			if(con != null) {
				con.close();
				System.out.println("Conexão fechada!");
			}
		}catch (Exception e) {
			//TODO: handle exceptiom
			throw new RuntimeException("Erro ao fechar conexão!", e);
		}


	}


	public void closeConnection(Connection con, PreparedStatement stmt) {
		closeConnection(con);
		try {
			if(stmt != null) {
				stmt.close();

			}


		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Erro ao fechar conexão!", e);
		}
	}

}

