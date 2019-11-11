package br.gov.pr.celepar.exemplo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe que representa uma entidade Serie.
 * @author GIC
 * @since 1.0
 * @version 1.0 - 17/04/19
 */
@Entity
@Table(name="serie",schema = "serie")
//@Table(name="serie",schema = "serie")
public class Serie implements Serializable { 
    
	private static final long serialVersionUID = -8795274013444946425L;
	
    @Id    
    @SequenceGenerator(name="SERIE_ID_SEQ", sequenceName="seq_serie_id_serie", schema = "serie")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SERIE_ID_SEQ")
    @Column(name = "id_serie", nullable = false)
	private Integer idSerie;
    
    @Column(name = "nome", nullable = false, unique=true)
    @NotNull(message="{serie.nome.notnull}")
    @Size(min=5, max=128, message="{serie.nome.size}")
    private String nome;
    
    //@JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serie")
//    private Set<Professor> professores;
    
    /** Construtor padrão. */
    public Serie() {
    }
    
    /**
     * Construtor alternativo.
     * @param idSerie
     * @param nome
     * @param matriculas
     */
    public Serie(Integer idSerie, String nome) {
        this.idSerie = idSerie;
        this.nome = nome;
    }

    public Integer getIdSerie() {
        return this.idSerie;
    }

    public void setIdSerie(Integer idSerie) {
        this.idSerie = idSerie;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome == null ? nome : nome.toUpperCase();
    }    


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSerie == null) ? 0 : idSerie.hashCode());
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
		Serie other = (Serie) obj;
		if (idSerie == null) {
			if (other.idSerie != null)
				return false;
		} else if (!idSerie.equals(other.idSerie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Serie [idSerie=" + idSerie + ", nome=" + nome  + "]";
	}
	
}
