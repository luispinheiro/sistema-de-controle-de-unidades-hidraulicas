package com.algaworks.brewer.model;

public enum Condicao {

	LIMPO("Limpo"),
	SUJO("Sujo");
	
	private String descricao;
	
	Condicao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
