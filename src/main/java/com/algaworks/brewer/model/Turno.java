package com.algaworks.brewer.model;

public enum Turno {

	PRIMEIRO("1º"),
	SEGUNDO("2º"),
	TERCEIRO("3º"),
	ADMIN("ADM");
	
	private String descricao;
	
	Turno(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
