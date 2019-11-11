package br.gov.pr.celepar.exemplo.util.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Classe que filtra as requisições HTTP, permitindo o consumo de serviços
 * REST via navegador.
 * 
 * @author GIC
 * @since 1.0 
 * @version 1.0 - 17/04/19
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {
	
	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext cres)
			throws IOException {


			cres.getHeaders().add("Access-Control-Allow-Origin", "*");
		
		cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHeaders().add("Access-Control-Expose-Headers", "Pagination-Count");
		cres.getHeaders().add("Access-Control-Expose-Headers", "Content-Disposition");
	}
	
	
}