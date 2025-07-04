package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private String codBarra;
    private String tipoUn;  // Ex: "UN", "KG", "LT", etc.
    private LocalDate dataFab;
    private LocalDate dataVal;
    private BigDecimal precoUn;  // Usando BigDecimal para valores monetários
    private int estoque;

    // Construtores
    public Produto() {
    }

    public Produto(int idProduto, String nomeProduto, String codBarra, String tipoUn, 
                  LocalDate dataFab, LocalDate dataVal, BigDecimal precoUn, int estoque) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.codBarra = codBarra;
        this.tipoUn = tipoUn;
        this.dataFab = dataFab;
        this.dataVal = dataVal;
        this.precoUn = precoUn;
        this.estoque = estoque;
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public String getTipoUn() {
        return tipoUn;
    }

    public void setTipoUn(String tipoUn) {
        this.tipoUn = tipoUn;
    }

    public LocalDate getDataFab() {
        return dataFab;
    }

    public void setDataFab(LocalDate dataFab) {
        this.dataFab = dataFab;
    }

    public LocalDate getDataVal() {
        return dataVal;
    }

    public void setDataVal(LocalDate dataVal) {
        this.dataVal = dataVal;
    }

    public BigDecimal getPrecoUn() {
        return precoUn;
    }

    public void setPrecoUn(BigDecimal precoUn) {
        this.precoUn = precoUn;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", codBarra='" + codBarra + '\'' +
                ", tipoUn='" + tipoUn + '\'' +
                ", dataFab=" + dataFab +
                ", dataVal=" + dataVal +
                ", precoUn=" + precoUn +
                ", estoque=" + estoque +
                '}';
    }
}