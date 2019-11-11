package br.gov.pr.celepar.exemplo.util.message;

import java.io.Serializable;
import java.util.List;

/**
 * Classe que encapsula lista de menssagens de erro.
 * @author GIC
 * @since 1.0 
 * @version 1.0 - 17/04/19
 */
public class MessageService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String message;	
	private List<MessageServiceError> errors;
		
	public MessageService(String message) {
		super();
		this.message = message;
	}
	
	public MessageService(String message, List<MessageServiceError> errors) {
		super();
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<MessageServiceError> getErrors() {
		return errors;
	}
	
	public void setErrors(List<MessageServiceError> errors) {
		this.errors = errors;
	}
}
