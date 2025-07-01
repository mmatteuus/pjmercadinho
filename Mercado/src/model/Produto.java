package model;

public class Produto {
	
	private String idFucionario;
	private String nomeFucionario;
	private String dataNasc;
	private String telefone; 
	private String endereco;
	private String email;
	private String cargo;
	private String nivel;
	
	
	public Produto(String idFucionario, String nomeFucionario, String dataNasc, String telefone, String endereco,
			String email, String cargo, String nivel) {
		super();
		this.idFucionario = idFucionario;
		this.nomeFucionario = nomeFucionario;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
		this.cargo = cargo;
		this.nivel = nivel;
	}


	public Produto() {
		super();
	}


	public String getIdFucionario() {
		return idFucionario;
	}


	public void setIdFucionario(String idFucionario) {
		this.idFucionario = idFucionario;
	}


	public String getNomeFucionario() {
		return nomeFucionario;
	}


	public void setNomeFucionario(String nomeFucionario) {
		this.nomeFucionario = nomeFucionario;
	}


	public String getDataNasc() {
		return dataNasc;
	}


	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public String getNivel() {
		return nivel;
	}


	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	
	

}
