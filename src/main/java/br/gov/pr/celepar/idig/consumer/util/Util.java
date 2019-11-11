package br.gov.pr.celepar.idig.consumer.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public final class Util {
	public static final String SESSION_ATTRIBUTE_USER_INFO = "THE_USER_INFO";
	public static final String SESSION_ATTRIBUTE_SERVER_AD = "THE_SERVER_AD";

	public 	static final String OAUTH_GRANT_TYPE = "grant_type";
	private static final String OAUTH_CODE = "code";
	private static final String OAUTH_STATE = "state";
	private static final String OAUTH_RESPONSE_TYPE = "response_type";
	private static final String OAUTH_CLIENT_ID = "client_id";
	private static final String OAUTH_REDIRECT_URI = "redirect_uri";
	public 	static final String OAUTH_SCOPE = "scope";

	private static final String REGEX_SIGNOUT = "^(.*)(/[Ss][Ii][Gg][Nn][Oo][Uu][Tt].*)$";
	private static final String REGEX_PUBLIC_OR_API = "^(.*)(/[Pp][Uu][Bb][Ll][Ii][Cc][Oo]/)(.*)$|^(.*)(/[Aa][Pp][Ii]/)(.*)$";
	private static final String REGEX_CERTAUTH = "^(.*)(/[Cc][Ee][Rr][Tt][Aa][Uu][Tt][Hh].*)$";
	public static final String AUTHORIZATION_PARAMETER_KEY = "Authorization";
	public static final String AUTHORIZATION_BEARER_PARAMETER_TEXT = "Bearer ";
	public static final String CONTENT_TYPE_PARAMETER_KEY = "Content-Type";
	public static final String CONTENT_TYPE_PARAMETER_VALUE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	public static final String COOKIE_TOKEN_NAME = "THE_TOKEN";
	public static final String COOKIE_REFRESH_TOKEN_NAME = "THE_REFRESH_TOKEN";
	private static final String COOKIE_STATE_NAME = "THE_STATE";
	private static final String COOKIE_REQUESTED_URI_NAME = "THE_REQUESTED_URI";
	private static final int EXPIRES_NOW = 0;
	private static final int EXPIRES_WHEN_EXIT = -1;
	private static final String AUTHORIZE_FORCE_LOGIN = "force_login";
	private static final String CLIENT_VERSION = "v0_0_12 - Testes de Empacotamento";
	private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
	private Util() {
	}

	public static boolean validarCPF(Long numero) throws Exception {
		if (numero == null || numero.equals(Long.valueOf(0))) {
			return false;
		}

		int nDig = 0;
		int d1 = 0;
		int d2 = 0;
		int digito1 = 0;
		int digito2 = 0;
		int resto = 0;
		String digVerif = "";
		StringBuilder digResul = new StringBuilder();
		String cpf = String.valueOf(numero);

		while (cpf.length() < 11) {
			cpf = "0" + cpf;
		}

		for (int i = 1; i < cpf.length() - 1; i++) {
			nDig = Integer.parseInt(cpf.substring(i - 1, i));
			d1 += (11 - i) * nDig; // Calculo do digito: Multiplicar ultima
			// casa por 2, penultima
			d2 += (12 - i) * nDig; // casa por 3, antepenultima casa por 4
			// e assim por diante
		}

		resto = (d1 % 11); // Se o resto for 0 ou 1 digito = 0, senao
		// digito = 11 menos o resto
		digito1 = (resto < 2) ? 0 : (11 - resto);
		d2 += 2 * digito1;
		resto = (d2 % 11);
		digito2 = (resto < 2) ? 0 : (11 - resto);
		digVerif = cpf.substring((cpf.length() - 2), cpf.length());
		digResul.append(digito1).append(digito2);

		return Integer.parseInt(digVerif) == Integer.parseInt(digResul.toString());
	}

	public static boolean validarCNPJ(Long numero) throws Exception {
		if (numero == null || numero.equals(Long.valueOf(0))) {
			return false;
		}

		int soma = 0;
		int dig = 0;
		String cnpj = String.valueOf(numero);
		StringBuilder cnpjResul = new StringBuilder();
		long cnpjCalc = 0L;

		while (cnpj.length() < 14) {
			cnpj = "0" + cnpj;
		}

		cnpjResul.append(cnpj.substring(0, 12));
		char[] nDig = cnpjResul.toString().toCharArray();

		// Primeira parte
		for (int i = 0; i < 4; i++) {
			if ((nDig[i] - 48 >= 0) && (nDig[i] - 48 <= 9)) {
				soma += (nDig[i] - 48) * (6 - (i + 1));
			}
		}

		for (int i = 0; i < 8; i++) {
			if ((nDig[i + 4] - 48 >= 0) && (nDig[i + 4] - 48 <= 9)) {
				soma += (nDig[i + 4] - 48) * (10 - (i + 1));
			}
		}

		dig = 11 - (soma % 11);
		cnpjResul.append(((dig == 10) || (dig == 11)) ? "0" : String.valueOf(dig));
		nDig = cnpjResul.toString().toCharArray();

		// Segunda parte
		soma = 0;

		for (int i = 0; i < 5; i++) {
			if ((nDig[i] - 48 >= 0) && (nDig[i] - 48 <= 9)) {
				soma += (nDig[i] - 48) * (7 - (i + 1));
			}
		}

		for (int i = 0; i < 8; i++) {
			if ((nDig[i + 5] - 48 >= 0) && (nDig[i + 5] - 48 <= 9)) {
				soma += (nDig[i + 5] - 48) * (10 - (i + 1));
			}
		}

		dig = 11 - (soma % 11);

		cnpjResul.append(((dig == 10) || (dig == 11)) ? "0" : String.valueOf(dig));

		cnpjCalc = Long.parseLong(cnpjResul.toString());

		return numero.longValue() == cnpjCalc;
	}

	public static String completarComZero(String valor, int tamanho, int lado) {
		String num = "";
		if (StringUtils.isNotBlank(valor)) {
			if (lado == 1) {
				num = StringUtils.leftPad(valor, tamanho, "0");
			} else if (lado == 2) {
				num = StringUtils.rightPad(valor, tamanho, "0");
			}
		}
		return num;
	}

	public static String formatarCPF(String numero) {

		if (StringUtils.isBlank(numero)) {
			return "000.000.000-00";
		}
		if (numero.contains(".") && numero.contains("-")) {
			return numero;
		}

		String zeros = "00000000000";
		int tamanho = numero.length();
		String auxiliar = numero;

		if (tamanho < 11) {
			auxiliar = zeros.substring(0, 11 - tamanho) + numero;
		}

		StringBuffer cpf = new StringBuffer();
		cpf.append(auxiliar.substring(0, 3));
		cpf.append(".");
		cpf.append(auxiliar.substring(3, 6));
		cpf.append(".");
		cpf.append(auxiliar.substring(6, 9));
		cpf.append("-");
		cpf.append(auxiliar.substring(9));

		return cpf.toString();
	}

	public static String formatarCEP(String cep) {
		return format("#####-###", cep);
	}

	private static String format(String pattern, Object value) {
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String, Cookie> getCookieMap(HttpServletRequest httpServletRequest) {
		Map<String, Cookie> cookies = new HashMap<String, Cookie>();

		try {
			for (Cookie cookie : httpServletRequest.getCookies()) {
				cookies.put(cookie.getName(), cookie);
			}
		} catch (Exception e) {
		
		}
		//Verifica se o AT foi passado via parametro
		try {
			if (cookies.get(COOKIE_TOKEN_NAME) == null) {
				
				String access_token = (String) httpServletRequest.getAttribute("access_token");
	
				if (isEmpty(access_token)) {
					access_token = (String) httpServletRequest.getParameter("access_token");
				}
				
				if (!isEmpty(access_token)) {
					Cookie cookieParametro = new Cookie(COOKIE_TOKEN_NAME, access_token);
					cookies.put(COOKIE_TOKEN_NAME, cookieParametro);
					
					Cookie theRefresh = new Cookie(COOKIE_REFRESH_TOKEN_NAME, "vazio");
					cookies.put(COOKIE_REFRESH_TOKEN_NAME, theRefresh);
					
				}
				
			}
		} catch (Exception e) {
		
		}
		
		return cookies;
	}
	
	/**
	 * Método responsável em obter o accessToken
	 * @param httpServletRequest
	 * @return
	 * @throws Exception 
	 */
	public static String obterAccessToken(HttpServletRequest httpServletRequest) throws Exception {
		
		Cookie theToken	= Util.getCookieMap(httpServletRequest).get(COOKIE_TOKEN_NAME);
		if (theToken == null){
			throw new Exception("Não foi possível obter accessToken, cookie vazio");
		}
		return theToken.getValue();
	}
	
	/**
	 * veirifca se a Sting está vazia
	 * @param value
	 * @return
	 */
	public static Boolean isEmpty(String value) {
		return value == null || "".equals(value);
	}
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date convertToDateViaInstant(LocalDate dateToConvert) {
	    return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
