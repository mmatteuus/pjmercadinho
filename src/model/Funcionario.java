package model;

public class Funcionario {
    private String idFuncionario;
    private String nomeFuncionario;
    private String cpfFuncionario;
    private String dataNasc;  // Mantido como String (formato dd/MM/yyyy)
    private String telefone;
    private String endereco;
    private String email;
    private String cargo;
    private String nivel;
    private String senha;

    // Construtores
    public Funcionario() {}

    public Funcionario(String idFuncionario, String nomeFuncionario, String cpfFuncionario, String dataNasc,
                       String telefone, String endereco, String email, String cargo, String nivel, String senha) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.cpfFuncionario = cpfFuncionario;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.cargo = cargo;
        this.nivel = nivel;
        this.senha = senha;
    }

    // Getters e Setters
    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "idFuncionario='" + idFuncionario + '\'' +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", cpfFuncionario='" + cpfFuncionario + '\'' +
                ", dataNasc='" + dataNasc + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
