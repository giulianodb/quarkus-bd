package br.gov.pr.celepar.exemplo.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import br.gov.pr.celepar.exemplo.dao.SerieDAO;
import br.gov.pr.celepar.exemplo.entity.Serie;
import br.gov.pr.celepar.exemplo.exception.ApplicationServiceException;
import br.gov.pr.celepar.exemplo.util.message.MessageBundle;
import br.gov.pr.celepar.exemplo.util.message.MessageServiceError;
import br.gov.pr.celepar.exemplo.util.validator.FieldValidator;

/**
 * Classe de negócio de Serie.
 * @author GIC
 * @since 1.0
 * @version 1.0 - 22/04/19
 */
@RequestScoped
public class SerieService {

	//private Logger log;
	
	@Inject
	SerieDAO serieDAO;
	
	/**
	 * Inclui a Serie.
	 * @param serie
	 * @throws ApplicationServiceException
	 */
	public void incluir(Serie serie) throws ApplicationServiceException {
		
//		// Valida parâmetros
//		List<MessageServiceError> listaErros = FieldValidator.validateFields(serie);		
//		if(listaErros.size() > 0) {
//			throw new ApplicationServiceException("message.parametrosnaoinformados", Response.Status.BAD_REQUEST.getStatusCode(), listaErros);
//		}
		
		try {
			serieDAO.incluir(serie);
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: incluir", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "incluir" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}		
	}
	
	/**
	 * Edita a Serie.
	 * @param serie
	 * @throws ApplicationServiceException
	 */
	public void alterar(Serie serie) throws ApplicationServiceException {
		
//		// Valida parâmetros
//		List<MessageServiceError> listaErros = FieldValidator.validateFields(serie);		
//		if(listaErros.size() > 0) {
//			throw new ApplicationServiceException("message.parametrosnaoinformados", Response.Status.BAD_REQUEST.getStatusCode(), listaErros);
//		}
//		
		// Verifica se a serie existe
		if(this.existe(serie.getIdSerie()) == false) {
			//log.debug("Debug na execucao do SerieBusiness: alterar = não existe - idSerie="+serie.getIdSerie());
			throw new ApplicationServiceException("serie.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
		}
				
		try {
			serieDAO.alterar(serie);
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: alterar", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "alterar" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}		
	}
	
	/**
	 * Exclui a serie por ID.
	 * @param idSerie
	 * @throws ApplicationServiceException
	 */
	public void excluir(Integer idSerie) throws ApplicationServiceException {
		
		//Valida parâmetro idSerie
		if(idSerie == null) {
			List<MessageServiceError> listaErros = new ArrayList<MessageServiceError>(0);
			listaErros.add(new MessageServiceError(MessageBundle.getMessage("serie.idserie"), "idSerie"));
			throw new ApplicationServiceException("message.parametrosnaoinformados", Response.Status.BAD_REQUEST.getStatusCode(), listaErros);
		}
		
		Serie serie = this.obterPorId(idSerie);
		
		//Valida se série existe		
		if(serie == null || serie.getIdSerie() == null) {
			//log.debug("Debug na execucao do SerieBusiness: excluir = não pode excluir = não existe - idSerie="+idSerie);
			throw new ApplicationServiceException("serie.naoexiste", Response.Status.BAD_REQUEST.getStatusCode());
		}
		
//		//Valida se existem professores vinculados
//		if(serie.getProfessores().size() >= 1) {
//			//log.debug("Debug na execucao do SerieBusiness: excluir = não pode excluir existe professor vinculado a serie");
//			throw new ApplicationServiceException("serie.naopodeserexcluida", Response.Status.BAD_REQUEST.getStatusCode());
//		}
//		
//		//Valida se existem matriculas vinculadas
//		if(serie.getMatriculas().size() >= 1) {
//			//log.debug("Debug na execucao do SerieBusiness: excluir = não pode excluir existe matriculas vinculadas a serie");
//			throw new ApplicationServiceException("serie.naopodeserexcluidamatriculas", Response.Status.BAD_REQUEST.getStatusCode());
//		}
		
		try {
			Serie s = new Serie();
			s.setIdSerie(idSerie);
			serieDAO.excluir(s);
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: excluir", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "excluir" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}	
	}
	
	/**
	 * Lista todas as series de forma paginada.
	 * @param page
	 * @param perPage
	 * @param sortField
	 * @param sortOrder
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public List<Serie> listar(Integer page, Integer perPage, String sortField, String sortOrder) throws ApplicationServiceException {
		
		List<Serie> series = new ArrayList<Serie>(0);
		try {
			series = serieDAO.listar(page, perPage, sortField, sortOrder);
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: listar", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "listar" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		} finally {
			if(series.size() <= 0) {
				//log.debug("Debug na execucao do SerieBusiness: listar = nenhum resultado");
				throw new ApplicationServiceException("serie.nenhumresultadolistagem", Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		
		return series;
	}
	
	/**
	 * Contagem do número de Series registradas
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public Integer obterQuantidade() throws ApplicationServiceException {		
		try {
			return serieDAO.obterQuantidade();
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: obterQuantidade", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "obterQuantidade" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
	}
	
	/**
	 * Recupera a Serie que contem o id informado.
	 * @param idSerie
	 * @return
	 * @throws ApplicationServiceException
	 */
	@Transactional
	public Serie obterPorId(Integer idSerie) throws ApplicationServiceException {
		
		//Valida parâmetro idSerie
		if(idSerie == null) {
			List<MessageServiceError> listaErros = new ArrayList<MessageServiceError>(0);
			listaErros.add(new MessageServiceError(MessageBundle.getMessage("serie.idserie"), "idSerie"));
			throw new ApplicationServiceException("message.parametrosnaoinformados", Response.Status.BAD_REQUEST.getStatusCode(), listaErros);
		}
		
		Serie serie = null;
		try {
			serie = serieDAO.obterPorId(idSerie);		 
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: obterPorId", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "obterPorId" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		} finally {
			if (serie == null) {
				//log.debug("Debug na execucao do SerieBusiness: obterPorId = nenhumresultado - CPF="+idSerie);
				throw new ApplicationServiceException("serie.naocadastrado", new String[] { "obterPorId" }, Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		
		return serie;
	}
	
	/**
	 *  Verifica se a Serie existe. 
	 * @param idSerie
	 * @return
	 * @throws ApplicationServiceException
	 */
	@Transactional
	private Boolean existe(Integer idSerie) throws ApplicationServiceException {
		
		Boolean existe = false;
		try {
			if (this.obterPorId(idSerie) != null) {
				existe = true;
				//log.debug("Debug na execucao do SerieBusiness: existe = true");
			}
		}  catch (ApplicationServiceException e) {
			//log.debug("Debug na execucao do SerieBusiness: existe = false", e);
			if(e.getStatusCode().equals(Response.Status.NOT_FOUND.getStatusCode())) {
				existe = false;
			} else throw e;
		}  catch (Exception e) {
			//log.error("Erro na execucao do SerieBusiness: existe", e);
			throw new ApplicationServiceException("serie.erro", new String[] { "existe" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		
		return existe;
	}
	
}
