package br.gov.pr.celepar.exemplo.util.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.gov.pr.celepar.exemplo.util.message.MessageServiceError;

/**
 * Classe que implementa funcionalidades de validação de campos (Bean Validation).
 * @author GIC
 * @version 1.0 - 24/04/19
 */
public class FieldValidator {

	/**
	 * Validação de Campos (ConstraintViolation).
	 * 
	 * @param objeto
	 * @return
	 */
	public static List<MessageServiceError> validateFields(Object obj) {
		return validateFields(obj, null);
	}

	/**
	 * Validação de Campos (ConstraintViolation).
	 * 
	 * @param objeto
	 * @param fields String[]{"field1","field2","field3"}
	 * @return
	 */
	public static List<MessageServiceError> validateFields(Object obj, String[] fields) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		List<MessageServiceError> listaErros = new ArrayList<MessageServiceError>(0);
		List<String> fieldList = null;
		if (fields != null && fields.length > 0) {
			fieldList = Arrays.asList(fields);
		}
		for (ConstraintViolation<Object> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			// se lista de campos == null então valida todos os campos
			// caso contrário valida apenas os campos informados
			if (fieldList == null || fieldList.contains(propertyPath)) {
				String message = violation.getMessage();
				MessageServiceError errorMessage = new MessageServiceError();
				errorMessage.setField(propertyPath);
				errorMessage.setMessage(message);
				listaErros.add(errorMessage);
			}
		}
		return listaErros;
	}

}
