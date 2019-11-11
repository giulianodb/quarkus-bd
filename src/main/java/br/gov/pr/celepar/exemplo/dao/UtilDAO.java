package br.gov.pr.celepar.exemplo.dao;

import java.util.HashMap;
import java.util.Iterator;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe com métodos genericos utilitarios para DAO.
 * @author GIC
 * @since 1.0
 * @version 1.0 - 22/04/19
 */@RequestScoped
public abstract class UtilDAO {
	
	/**
	 * Quantidade máxima de resultados por página, para manter desempenho.
	 */
	public static final int MAX_RESULT_PER_PAGE = 50;
	
	/**
	 * Método que monta o Sorter para consultas
	 * @param sortField - Coluna de ordenação
	 * @param sortOrder - SorterOrder: ASCENDING, ASC, DESCENDING, DESC
	 * @param query - Query
	 */	
	public static void sorterQuery(String aliasObject, String sortField, String sortOrder, StringBuilder query) {
		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(aliasObject) && StringUtils.isNotBlank(sortOrder)) {
			query.append(" ORDER BY ");
			if(sortOrder.equals("ASCENDING") || sortOrder.equals("ASC")) {
				query.append(aliasObject).append(".").append(sortField).append(" ASC");
			} else if (sortOrder.equals("DESCENDING") || sortOrder.equals("DESC")) {
				query.append(aliasObject).append(".").append(sortField).append(" DESC");
			}				
		}
	}
	
	/**
	 * Método usado para setar os parametros de acordo com atributos de pesquisa
	 * @param parameters
	 * @param query
	 */
	public static void setParameters(HashMap<String, Object> parameters, Query query) {
		Iterator<String> keySetIterator = parameters.keySet().iterator();
		while(keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			query.setParameter(key, parameters.get(key));
		}
	}
	
	/**
	 * Método usado para fazer a lógica de paginação.
	 * @param tQuery
	 * @param page
	 * @param perPage
	 */
	public static <T> void setPagination(TypedQuery<T> tQuery, Integer page, Integer perPage) {
		if (page == null) {
			page = 1;
		}
		if (perPage == null) {
			perPage = 10;
		}
		
		//Delimita o num de registro para a pagina a ser recuperada
		page = page > 0 ? (page-1) : 0;
		perPage = perPage <= MAX_RESULT_PER_PAGE ? perPage : MAX_RESULT_PER_PAGE;
        tQuery.setFirstResult(page * perPage);
        tQuery.setMaxResults(perPage);
	}
	
}
