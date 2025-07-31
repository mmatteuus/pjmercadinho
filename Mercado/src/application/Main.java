package application;
	
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import dao.ClienteDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Cliente;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static Stage stage;
	private static Scene main;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			stage = primaryStage;
			
			Parent fxmllogin = FXMLLoader.load(getClass().getResource("/view/viewLogin.fxml"));
			main = new Scene(fxmllogin);
			
			primaryStage.setScene(main);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		
	Cliente	cliente = new Cliente();
	ClienteDAO clienteDAO = new ClienteDAO();
	ArrayList<Cliente> clientes = new ArrayList<>();
	
	cliente.setNomeCliente("Cabral");
	cliente.setCpfCliente("86578698676");
	cliente.setDataNasc("2005-03-15");
	cliente.setTelefone("639920345");
	cliente.setEmail("cabral@gmail.com");
	cliente.setEndereco("rua do espertos");
	
//	clienteDAO.updata(cliente);
	
//	cliente.delete("86578698676");
	
	
	clientes = clienteDAO.search("ana");

	for(int i = 0; i < clientes.size(); i++) {
		cliente = clientes.get(i);
		System.out.print("||");
		System.out.print(cliente.getIdCliente());
		System.out.print("|");
		System.out.print(cliente.getNomeCliente());
		System.out.print("|");
		System.out.print(cliente.getCpfCliente());
		System.out.print("|");
		System.out.print(cliente.getDataNasc());
		System.out.print("|");
		System.out.print(cliente.getTelefone());
		System.out.print("|");
		System.out.print(cliente.getEmail());
		System.out.print("|");
		System.out.print(cliente.getEndereco());
		System.out.println("");
		
	}

		
		launch(args);
	}
	
	
	
	
	
	
}
