package model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Funcionario {
	private int idFuncionario;
	private String nomeFuncionario;
	private String cpfFuncionario;  // Corrigido o nome do campo (de 'cpfFucionario')
	private LocalDate dataNasc;      // Alterado para LocalDate
	private String telefone;
	private String endereco;
	private String email;
	private String cargo;            // Adicionado campo cargo
	private int nivel;               // Alterado para int

	// Construtores
	public Funcionario() {
	}

	public Funcionario(int idFuncionario, String nomeFuncionario, String cpfFuncionario, LocalDate dataNasc,
			String telefone, String endereco, String email, String cargo, int nivel) {
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.cpfFuncionario = cpfFuncionario;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
		this.cargo = cargo;
		this.nivel = nivel;
	}

	// Getters e Setters
	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date date) {
		this.dataNasc = date;
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

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Funcionario{" +
				"idFuncionario=" + idFuncionario +
				", nomeFuncionario='" + nomeFuncionario + '\'' +
				", cpfFuncionario='" + cpfFuncionario + '\'' +
				", dataNasc=" + dataNasc +
				", telefone='" + telefone + '\'' +
				", endereco='" + endereco + '\'' +
				", email='" + email + '\'' +
				", cargo='" + cargo + '\'' +
				", nivel=" + nivel +
				'}';
	}
}