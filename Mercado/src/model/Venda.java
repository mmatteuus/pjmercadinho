package model;

public class Venda {
	
	private String idVendas;
	private String idCliente;
	private String idFucionario;
	private String dataVenda;
	private String precoTotal;
	private String formaPag;
	private String quantTotal;
	
	
	public Venda(String idVendas, String idCliente, String idFucionario, String dataVenda, String precoTotal,
			String formaPag, String quantTotal) {
		super();
		this.idVendas = idVendas;
		this.idCliente = idCliente;
		this.idFucionario = idFucionario;
		this.dataVenda = dataVenda;
		this.precoTotal = precoTotal;
		this.formaPag = formaPag;
		this.quantTotal = quantTotal;
	}


	public Venda() {
		super();
	}


	public String getIdVendas() {
		return idVendas;
	}


	public void setIdVendas(String idVendas) {
		this.idVendas = idVendas;
	}


	public String getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}


	public String getIdFucionario() {
		return idFucionario;
	}


	public void setIdFucionario(String idFucionario) {
		this.idFucionario = idFucionario;
	}


	public String getDataVenda() {
		return dataVenda;
	}


	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}


	public String getPrecoTotal() {
		return precoTotal;
	}


	public void setPrecoTotal(String precoTotal) {
		this.precoTotal = precoTotal;
	}


	public String getFormaPag() {
		return formaPag;
	}


	public void setFormaPag(String formaPag) {
		this.formaPag = formaPag;
	}


	public String getQuantTotal() {
		return quantTotal;
	}


	public void setQuantTotal(String quantTotal) {
		this.quantTotal = quantTotal;
	}
	
	
	
	

}
