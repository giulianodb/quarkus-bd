package br.gov.pr.celepar.exemplo.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.gov.pr.celepar.exemplo.entity.Serie;
import br.gov.pr.celepar.exemplo.exception.ApplicationServiceException;

/**
 * Classe de manipulação de objetos da classe Serie.
 * @author GIC
 * @since 1.0
 * @version 1.0 - 22/04/19
 */
@RequestScoped
public class SerieDAO {

	@PersistenceContext
	EntityManager em;
	
	/**
	 * Cadastro de serie.
	 * @param serie
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public void incluir(Serie serie) throws ApplicationServiceException {
		try {
			em.persist(serie);
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", e);
		}
	}
	
	/**
	 * Alteração de serie.
	 * @param serie
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public void alterar(Serie serie) throws ApplicationServiceException {
		try {
			em.merge(serie);
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", e);
		}
	}
	
	/**
	 * Exclusao de serie.
	 * @param serie
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public void excluir(Serie serie) throws ApplicationServiceException {
		try {
			serie = em.find(Serie.class,serie.getIdSerie());
			em.remove(serie);
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", e);
		}
	}
	
	/**
	 * Listar todas as series.
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public List<Serie> listar() throws ApplicationServiceException {
		try {
			TypedQuery<Serie> query = em.createQuery("SELECT s FROM Serie AS s ORDER BY s.nome",Serie.class);		 				
			return query.getResultList();
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", e);
		}
	}
		
	/**
	 * Listar todas as series de forma paginada. 
	 * @param page
	 * @param perPage
	 * @param sortField
	 * @param sortOrder
	 * @return
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public List<Serie> listar(Integer page, Integer perPage, String sortField, String sortOrder) throws ApplicationServiceException {
		try {		
			StringBuilder query = new StringBuilder("SELECT serie FROM Serie AS serie ");
			UtilDAO.sorterQuery("serie", sortField, sortOrder, query);
			TypedQuery<Serie> tQuery = em.createQuery(query.toString(),Serie.class);
			
			UtilDAO.sorterQuery("serie", sortField, sortOrder, query);
			
			//Delimita o num de registro para a pagina a ser recuperada
			UtilDAO.setPagination(tQuery, page, perPage);
			
			return tQuery.getResultList();
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", new String[] { "listar" } , e);
		}
	}
	
	/**
	 * Contagem do numero de registros obtidos na Listagem de todas as series
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public Integer obterQuantidade() throws ApplicationServiceException {
		try {
			Query query = em.createQuery("SELECT COUNT(s) FROM Serie s");
			Long x = (Long) query.getSingleResult();		
			return Integer.valueOf(x.intValue());
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", e);
		}
	}
	
	/**
	 * Recupera pelo CPF informado. Caso nao encontre, retorna null.
	 * @param cpf
	 * @return
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public Serie obterPorId(Integer idSerie) throws ApplicationServiceException {
		try {
			TypedQuery<Serie> query = em.createQuery("SELECT s FROM Serie AS s WHERE s.idSerie = :idSerie", Serie.class);
			query.setParameter("idSerie", idSerie);
			return query.getSingleResult();
		} catch(NoResultException nre){
			return null;
		} catch(Exception e) {
			throw new ApplicationServiceException("serie.erro", new String[] { "obterPorId" } , e);
		}
	}
	
}