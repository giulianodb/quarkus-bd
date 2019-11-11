package br.gov.pr.celepar.exemplo.resources.vo;

import java.io.Serializable;

import br.gov.pr.celepar.exemplo.entity.Serie;

/**
 * Classe que representa dados da Serie.
 * @author GIC
 * @since 1.0 
 * @version 1.0 - 17/04/19
 */
public class SerieVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idSerie;
    private String nome;

    /**
	 * Construtor padrão.
	 */
    public SerieVO() {
    }
    
    /**
	 * Construtor que transforma um objeto Entity em um objeto VO.
	 * @param professor
	 */
    public SerieVO(Serie serie) {
    	this.idSerie = serie.getIdSerie();
    	this.nome = serie.getNome();
    }
    
    /**
	 * Transforma um objeto VO em um objeto Entity.
	 * @return
	 */
    public Serie toEntity() {
    	Serie s = new Serie();
    	s.setNome(nome);
    	s.setIdSerie(this.idSerie);
    	
    	return s;
    }
    
	public Integer getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "SerieVO [idSerie=" + idSerie + ", nome=" + nome + "]";
	}
    
}
