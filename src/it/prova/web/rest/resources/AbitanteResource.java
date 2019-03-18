package it.prova.web.rest.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.prova.model.Abitante;
import it.prova.service.abitante.AbitanteService;
import it.prova.service.municipio.MunicipioService;
import it.prova.web.dto.abitante.AbitanteDTO;
import it.prova.web.dto.municipio.MunicipioDTO;

@Component
@Path("/abitante")
public class AbitanteResource {

	@Autowired
	MunicipioService municipioService;

	@Autowired
	AbitanteService abitanteService;

	@GET
	@Path("{id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAbitante(@PathParam("id") Long id) {
		return Response.status(200).entity(new AbitanteDTO(abitanteService.caricaSingoloAbitante(id))).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertNuovoAbitante(@QueryParam("idMunicipio") Long idMunicipio, Abitante abitanteInput) {

		HashMap<String, String> listaErrori = new AbitanteDTO(abitanteInput).validate();

		if (!listaErrori.isEmpty()) {
			return Response.status(Response.Status.FORBIDDEN).entity(listaErrori).build();
		}

		abitanteInput.setMunicipio(municipioService.caricaSingoloMunicipioConAbitanti(idMunicipio));
		abitanteService.inserisciNuovo(abitanteInput);

		return Response.status(200).entity(new AbitanteDTO(abitanteInput)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		return Response.status(200).entity(AbitanteDTO.listAbitanteDTO(abitanteService.listAllAbitanti())).build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchAbitante(@QueryParam("nome") String nome, @QueryParam("cognome") String cognome,
			@QueryParam("eta") Integer eta, @QueryParam("residenza") String residenza) {
		System.out.println(nome + " " + cognome + " " + eta + " " + residenza);
		List<Abitante> resultTemp = abitanteService.findByExample(new Abitante(nome, cognome, eta, residenza));
		Set<AbitanteDTO> result = AbitanteDTO.listAbitanteDTO(resultTemp);
		return Response.status(200).entity(result).build();
	}

	@DELETE
	@Path("{id : \\d+}")
	public Response deleteAbitante(@PathParam("id") Long id) {
		abitanteService.rimuovi(abitanteService.caricaSingoloAbitante(id));
		return Response.status(200).entity("Rimossa Persona avente id: " + id).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response aggiornaAbitante(@QueryParam("idMunicipio") Long idMunicipio, AbitanteDTO abitanteDTOInput) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		if (idMunicipio != null) {
			abitanteDTOInput.setMunicipio(MunicipioDTO.buildMunicipioDTO(municipioService.caricaSingoloMunicipioConAbitanti(idMunicipio)));
		} else {
			abitanteDTOInput.setMunicipio(MunicipioDTO.buildMunicipioDTO(abitanteService.caricaSingoloAbitante(abitanteDTOInput.getId()).getMunicipio()));			
		}

		HashMap<String, String> listaErrori = abitanteDTOInput.validate();

		if (!listaErrori.isEmpty()) {
			return Response.status(Response.Status.FORBIDDEN).entity(listaErrori).build();
		}
		
		Abitante abitanteDaMod = new Abitante();
		
		PropertyUtils.copyProperties(abitanteDaMod, abitanteDTOInput);
		
		abitanteService.aggiorna(abitanteDaMod);
		
		return Response.status(200).entity(abitanteDTOInput).build();
	}
}
