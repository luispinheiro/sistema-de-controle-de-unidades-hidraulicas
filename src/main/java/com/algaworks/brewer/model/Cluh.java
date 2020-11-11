package com.algaworks.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "cluh")
public class Cluh implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@javax.validation.constraints.NotNull(message = "Nível é obrigatório")
	@javax.validation.constraints.Max(value = 100, message = "Nível não pode ser maior que 100%")
	@javax.validation.constraints.DecimalMin(value = "0", message = "Nível não pode ser menor que 0%")
	private Integer nivel;
	
	@javax.validation.constraints.NotNull(message = "Abastecimento é obrigatório")
	@javax.validation.constraints.DecimalMin(value = "0", message = "Abastecimento deve ser maior que 0 Litro")
	@javax.validation.constraints.DecimalMax(value = "599.5", message = "Abastecimento deve ser menor que 500 Listros")
	private BigDecimal abastecimento;
	
	@javax.validation.constraints.NotNull(message = "Condição é obrigatório")
	@Enumerated(EnumType.STRING)
	private Condicao condicao;

	private String observacao;
	
	@javax.validation.constraints.NotNull(message = "TAG é obrigatório")
	@ManyToOne
	@JoinColumn(name = "tag_id")
	private Tag tag;
	
	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

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

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isNovo() {
		return id == null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cluh other = (Cluh) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
