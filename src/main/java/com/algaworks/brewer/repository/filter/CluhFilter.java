package com.algaworks.brewer.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.brewer.model.Condicao;
import com.algaworks.brewer.model.Tag;

public class CluhFilter {

	private LocalDate dataDe;
	
	private LocalDate dataAte;

	private String turnoUsuario;
	
	private String nomeUsuario;
	
	private Integer NivelDe;
	
	private Integer NivelAte;
	
	private BigDecimal abastecidoDe;
	
	private BigDecimal abastecidoAte;
	
	private Condicao condicao;

	private String observacao;

	private Tag tag;

	public LocalDate getDataDe() {
		return dataDe;
	}

	public void setDataDe(LocalDate dataDe) {
		this.dataDe = dataDe;
	}

	public LocalDate getDataAte() {
		return dataAte;
	}

	public void setDataAte(LocalDate dataAte) {
		this.dataAte = dataAte;
	}

	public String getTurnoUsuario() {
		return turnoUsuario;
	}

	public void setTurnoUsuario(String turnoUsuario) {
		this.turnoUsuario = turnoUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public Integer getNivelDe() {
		return NivelDe;
	}

	public void setNivelDe(Integer nivelDe) {
		NivelDe = nivelDe;
	}

	public Integer getNivelAte() {
		return NivelAte;
	}

	public void setNivelAte(Integer nivelAte) {
		NivelAte = nivelAte;
	}

	public BigDecimal getAbastecidoDe() {
		return abastecidoDe;
	}

	public void setAbastecidoDe(BigDecimal abastecidoDe) {
		this.abastecidoDe = abastecidoDe;
	}

	public BigDecimal getAbastecidoAte() {
		return abastecidoAte;
	}

	public void setAbastecidoAte(BigDecimal abastecidoAte) {
		this.abastecidoAte = abastecidoAte;
	}

	public Condicao getCondicao() {
		return condicao;
	}

	public void setCondicao(Condicao condicao) {
		this.condicao = condicao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
