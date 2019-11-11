package br.gov.pr.celepar.exemplo.resources;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.gov.pr.celepar.exemplo.exception.ApplicationServiceException;
import br.gov.pr.celepar.exemplo.resources.vo.SerieVO;
import br.gov.pr.celepar.exemplo.services.SerieService;
import br.gov.pr.celepar.exemplo.util.message.MessageBundle;
import br.gov.pr.celepar.exemplo.util.message.MessageService;

/**
 * Classe provedora de serviços rest de Serie. 
 * @author GIC
 * @since 1.0 
 * @version 1.0 - 17/04/19
 */
@Path("v1/serie")
public class SerieResource {

	//private Logger log;
	
	@Inject
	SerieService serieService;
	
	/**
	 * Lista Series.
	 * @param page
	 * @param perPage
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("page") Integer page, 
			@QueryParam("perPage") Integer perPage,
			@QueryParam("sortField") String sortField, 
			@QueryParam("sortOrder") String sortOrder) {

		List<SerieVO> series = new ArrayList<SerieVO>(0);
		Integer quantidadeTotalListagem = 0;
		try {
			series = serieService.listar(page, perPage, sortField, sortOrder)
					.stream().map(e -> new SerieVO(e))
					.collect(Collectors.toList());
			
			quantidadeTotalListagem = serieService.obterQuantidade();
			
			
		} catch (ApplicationServiceException appEx) {
			//log.debug("Problema na execucao do SerieService: listar", appEx);
			return Response.status(appEx.getStatusCode())
					.entity(new MessageService(appEx.getMessage()))
					.build();
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieService: listar", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new MessageService(MessageBundle.getMessage("serie.erro", new String[] { "listar" })))
					.build();
		}
				
		return Response.ok().entity(series)
				.header("Pagination-Count", quantidadeTotalListagem)
				.build();
	}
	
	/**
	 * Obter Serie por ID.
	 * @param idSerie
	 * @return
	 */
	@GET
	@Path("/{idSerie}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response obterPorId(@PathParam("idSerie") Integer idSerie, @Context SecurityContext ctx) {
		
		Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        System.out.println("==================");
        System.out.println(name);
        System.out.println("==================");
		
		SerieVO serieVO = null;
		try {
			serieVO = new SerieVO(serieService.obterPorId(idSerie));
		} catch (ApplicationServiceException appEx) {
			//log.debug("Problema na execucao do SerieService: obterPorId", appEx);
			return Response.status(appEx.getStatusCode())
					.entity(new MessageService(appEx.getMessage(), appEx.getErrorList()))
					.build();
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieService: obterPorId", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new MessageService(MessageBundle.getMessage("serie.erro", new String[] { "obter" })))
					.build();
		}
		
		return Response.status(Response.Status.OK).entity(serieVO).build();
	}
	
	
	/**
	 * Incluir Serie.
	 * @param serieVO
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//@OauthHandler(grupos={"VJE-Coordenacao","VJE-Administrador"})
	public Response incluir(SerieVO serieVO) {
		
		URI location = null;
		try {
			serieService.incluir(serieVO.toEntity());
			location = new URI("v1/serie/"+serieVO.getIdSerie());
		} catch (ApplicationServiceException appEx) {
			//log.debug("Problema na execucao do SerieService: incluir", appEx);
			return Response.status(appEx.getStatusCode())
					.entity(new MessageService(appEx.getMessage(), appEx.getErrorList()))
					.build();
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieService: incluir", e);
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new MessageService(MessageBundle.getMessage("serie.erro", new String[] { "incluir" })))
					.build();
		}
		
		return Response.status(Response.Status.CREATED)
				.contentLocation(location)
				.build();
	}
	
	/**
	 * Alterar Serie.
	 * @param serieVO
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//@OauthHandler(grupos={"VJE-Coordenacao","VJE-Administrador"})
	public Response alterar(SerieVO serieVO) {
		
		try {
			serieService.alterar(serieVO.toEntity());
		} catch (ApplicationServiceException appEx) {
			//log.debug("Problema na execucao do SerieService: alterar", appEx);
			return Response.status(appEx.getStatusCode())
					.entity(new MessageService(appEx.getMessage(), appEx.getErrorList()))
					.build();
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieService: alterar", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new MessageService(MessageBundle.getMessage("serie.erro", new String[] { "alterar" })))
					.build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	/**
	 * Deletar Serie.
	 * @param idSerie
	 * @return
	 */
	@DELETE
	@Path("/{idSerie}")
	@Produces(MediaType.APPLICATION_JSON)
	//@OauthHandler(grupos={"VJE-Coordenacao","VJE-Administrador"})
	public Response excluir(@PathParam("idSerie") Integer idSerie) {
		
		try {
			serieService.excluir(idSerie);
		} catch (ApplicationServiceException appEx) {
			//log.debug("Problema na execucao do SerieService: excluir", appEx);
			return Response.status(appEx.getStatusCode())
					.entity(new MessageService(appEx.getMessage(), appEx.getErrorList()))
					.build();
		} catch (Exception e) {
			//log.error("Erro na execucao do SerieService: excluir", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new MessageService(MessageBundle.getMessage("serie.erro", new String[] { "excluir" })))
					.build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
}