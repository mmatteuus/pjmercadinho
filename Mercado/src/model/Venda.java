package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Venda {
    private int idVenda;
    private int idCliente;
    private int idFuncionario;
    private LocalDate dataVenda;
    private BigDecimal precoTotal;
    private String formaPag;
    private int quantTotal;

    // Construtores
    public Venda() {
    }

    public Venda(int idCliente, int idFuncionario, LocalDate dataVenda, 
                 BigDecimal precoTotal, String formaPag, int quantTotal) {
        this.idCliente = idCliente;
        this.idFuncionario = idFuncionario;
        this.dataVenda = dataVenda;
        this.precoTotal = precoTotal;
        this.formaPag = formaPag;
        this.quantTotal = quantTotal;
    }

    public Venda(int idVenda, int idCliente, int idFuncionario, LocalDate dataVenda, 
                 BigDecimal precoTotal, String formaPag, int quantTotal) {
        this.idVenda = idVenda;
        this.idCliente = idCliente;
        this.idFuncionario = idFuncionario;
        this.dataVenda = dataVenda;
        this.precoTotal = precoTotal;
        this.formaPag = formaPag;
        this.quantTotal = quantTotal;
    }

    // Getters e Setters
    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

    public int getQuantTotal() {
        return quantTotal;
    }

    public void setQuantTotal(int quantTotal) {
        this.quantTotal = quantTotal;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", idCliente=" + idCliente +
                ", idFuncionario=" + idFuncionario +
                ", dataVenda=" + dataVenda +
                ", precoTotal=" + precoTotal +
                ", formaPag='" + formaPag + '\'' +
                ", quantTotal=" + quantTotal +
                '}';
    }
}