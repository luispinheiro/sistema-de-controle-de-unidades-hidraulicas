package com.algaworks.brewer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.algaworks.brewer.model.Condicao;

public class CluhDTO {

	private Long id;
	private LocalDateTime dataCriacao;
	private Integer nivel;
	private BigDecimal abastecimento;
	private Condicao condicao;
	private String observacao;
	
	public CluhDTO(Long id, LocalDateTime dataCriacao, Integer nivel, BigDecimal abastecimento, Condicao condicao,
			String observacao) {
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.nivel = nivel;
		this.abastecimento = abastecimento;
		this.condicao = condicao;
		this.observacao = observacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public BigDecimal getAbastecimento() {
		return abastecimento;
	}

	public void setAbastecimento(BigDecimal abastecimento) {
		this.abastecimento = abastecimento;
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

}
