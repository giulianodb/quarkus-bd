package br.gov.pr.celepar.exemplo.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class MyResponseExceptionMapper implements ResponseExceptionMapper<SomeException> {

	@Override
	public SomeException toThrowable(Response response) {
		System.out.println(response.getStatus());
		return new SomeException();
	}
}