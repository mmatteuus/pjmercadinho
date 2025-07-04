package model;

import java.math.BigDecimal;

public class VendaProduto {
    private int idVendaProduto;
    private int idVenda;
    private int idProduto;
    private int quantidade;
    private BigDecimal valorTotal;

    // Construtores
    public VendaProduto() {
    }

    public VendaProduto(int idVenda, int idProduto, int quantidade, BigDecimal valorTotal) {
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public VendaProduto(int idVendaProduto, int idVenda, int idProduto, int quantidade, BigDecimal valorTotal) {
        this.idVendaProduto = idVendaProduto;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public int getIdVendaProduto() {
        return idVendaProduto;
    }

    public void setIdVendaProduto(int idVendaProduto) {
        this.idVendaProduto = idVendaProduto;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "VendaProduto{" +
                "idVendaProduto=" + idVendaProduto +
                ", idVenda=" + idVenda +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                '}';
    }
}