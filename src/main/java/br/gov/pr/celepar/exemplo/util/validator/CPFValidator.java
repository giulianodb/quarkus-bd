package br.gov.pr.celepar.exemplo.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe Bean Validator de CPF.
 * @author GIC
 * @since 1.0 
 * @version 1.0 - 17/04/19
 */
public class CPFValidator implements ConstraintValidator<CPF, String> {

	@Override
	public void initialize(CPF constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(StringUtils.isBlank(value)){
			return false;
		}
		
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (value.equals("00000000000") || value.equals("11111111111") || value.equals("22222222222")
				|| value.equals("33333333333") || value.equals("44444444444") || value.equals("55555555555")
				|| value.equals("66666666666") || value.equals("77777777777") || value.equals("88888888888")
				|| value.equals("99999999999") || (value.length() != 11))
			return false;

		//String strCpf = value;

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < value.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(value.substring(nCount - 1, nCount)).intValue();
			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;
			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = value.substring(value.length() - 2, value.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}
}