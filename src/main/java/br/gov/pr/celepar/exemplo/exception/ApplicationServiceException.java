package br.gov.pr.celepar.exemplo.exception;

import java.util.List;

import br.gov.pr.celepar.exemplo.util.message.MessageBundle;
import br.gov.pr.celepar.exemplo.util.message.MessageServiceError;

/**
 * Classe utilizada para encapsular exceções da aplicação.
 * @author grupo Framework - Componentes
 * @since 1.0 
 * @version 1.1, 29/04/2005
 */
public class ApplicationServiceException extends Exception {
	
	/** Serial Id. **/
	private static final long serialVersionUID = 2013512271265915763L;
	

	/**  Guarda excecao ocorrida. **/
	private Throwable rootCause = null;
	
	/**
	 *  HTTP status of the response .
	 */
	private Integer statusCode = 500;
	
	/**
	 * Guarda a lista de erros.
	 */
	private List<MessageServiceError> errorList;
	
	/**
	 * Constrói e recupera a mensagem a partir da chave informada.
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 */
	public ApplicationServiceException(String messageKeyLoc) {
		super(MessageBundle.getMessage(messageKeyLoc));
	}
	
	/**
	 * Constrói e recupera a mensagem a partir da chave informada.
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param statusCode 
	 */
	public ApplicationServiceException(String messageKeyLoc, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc));		
		this.statusCode = statusCode;
	}
	
	/**
	 * Constrói e recupera a mensagem a partir da chave informada.
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param statusCode codigo de erro de serviço
	 * @param errorList lista de erros
	 */
	public ApplicationServiceException(String messageKeyLoc, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc));
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
		
	/**
	 * Constrói e recupera a mensagem a partir da chave informada substituindo
	 * as chaves encontradas na mensagem pelos parâmetros passados no array.
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param parameters parâmetros que serão substituídos na mensagem
	 */
	public ApplicationServiceException(String messageKeyLoc, String[] parameters) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
	}
	
	/**
	 * Constrói e recupera a mensagem a partir da chave informada substituindo
	 * as chaves encontradas na mensagem pelos parâmetros passados no array.
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param parameters parâmetros que serão substituídos na mensagem
	 * @param statusCode 
	 */
	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		this.statusCode = statusCode;
	}
	
	/**
	 * Constrói e recupera a mensagem a partir da chave informada substituindo
	 * as chaves encontradas na mensagem pelos parâmetros passados no array.
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param parameters parâmetros que serão substituídos na mensagem
	 * @param statusCode 
	 * @param errorList
	 */
	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
	
	/**
	 * Constrói objeto com a pilha de exceção e com mensagem padrão. 
	 * 
	 * @param causa causa raiz da exceção ocorrida
	 */
	public ApplicationServiceException(Throwable causa) {
		super(MessageBundle.getMessage("message.default"));
		setRootCause(causa);
	}
	
	/**
	 * Constrói objeto com a pilha de exceção e com mensagem padrão. 
	 * 
	 * @param causa causa raiz da exceção ocorrida
	 * @param statusCode
	 */
	public ApplicationServiceException(Throwable causa, Integer statusCode) {
		super(MessageBundle.getMessage("message.default"));
		setRootCause(causa);
		this.statusCode = statusCode;
	}
	
	/**
	 * Constrói objeto com a pilha de exceção e com mensagem padrão. 
	 * 
	 * @param causa causa raiz da exceção ocorrida
	 * @param statusCode
	 * @param errorList
	 */
	public ApplicationServiceException(Throwable causa, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage("message.default"));
		setRootCause(causa);
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
		
	/**
	 * Constrói objeto com a pilha de exceção e recupera a 
	 * mensagem a partir da chave informada.
	 * 
	 * @param messageKeyLoc chave para a busca da mensagem
	 * @param causa causa raiz da exceção ocorrida
	 */
	public ApplicationServiceException(String messageKeyLoc, Throwable causa) {
		super(MessageBundle.getMessage(messageKeyLoc));
		setRootCause(causa);
	}
	
	/**
	 * Constrói objeto com a pilha de exceção e recupera a 
	 * mensagem a partir da chave informada.
	 * 
	 * @param messageKeyLoc chave para a busca da mensagem
	 * @param causa causa raiz da exceção ocorrida
	 * @param statusCode
	 */
	public ApplicationServiceException(String messageKeyLoc, Throwable causa, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc));
		setRootCause(causa);
		this.statusCode = statusCode;
	}
	
	/**
	 * Constrói objeto com a pilha de exceção e recupera a 
	 * mensagem a partir da chave informada.
	 * 
	 * @param messageKeyLoc chave para a busca da mensagem
	 * @param causa causa raiz da exceção ocorrida
	 * @param statusCode
	 * @param errorList
	 */
	public ApplicationServiceException(String messageKeyLoc, Throwable causa, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc));
		setRootCause(causa);
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
			
	/**
	 * Constrói objeto com a pilha de exceção e recupera a mensagem a partir 
	 * da chave informada substituindo as chaves encontradas na mensagem pelos 
	 * parâmetros passados no array.
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param parameters parâmetros que serão substituídos na mensagem
	 * @param rootCause causa raiz da exceção ocorrida
	 */
	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
	}
	
	/**
	 * Constrói objeto com a pilha de exceção e recupera a mensagem a partir 
	 * da chave informada substituindo as chaves encontradas na mensagem pelos 
	 * parâmetros passados no array.
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param parameters parâmetros que serão substituídos na mensagem
	 * @param rootCause causa raiz da exceção ocorrida
	 * @param statusCode
	 */
	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
		this.statusCode = statusCode;
	}
	
	/**
	 * 
	 * @param messageKeyLoc chave para busca da mensagem
	 * @param parameters parâmetros que serão substituídos na mensagem
	 * @param rootCause causa raiz da exceção ocorrida
	 * @param statusCode 
	 * @param errorList
	 */
	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
	
	/**
	 * @return causa raiz
	 */
	public Throwable getRootCause() {
		return rootCause;
	}
	
	/**
	 * @param rootCause causa raiz da exceção ocorrida
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public List<MessageServiceError> getErrorList() {
		return errorList;
	}
	
}
