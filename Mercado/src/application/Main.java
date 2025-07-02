package application;
	
import dao.ClienteDAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Cliente;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();
		
		cliente.setNomeCliente("Pedro Alvarez");
		cliente.setCpfCliente("00011122233");
		cliente.setDataNasc("1995-11-01");
		cliente.setTelefone("63992000001");
		cliente.setEndereco("Rua dos bobos, n° 0");
		cliente.setEmail("pedro@gmail.com");
		
		clienteDAO.create(cliente);
				
		launch(args);
	}
}
