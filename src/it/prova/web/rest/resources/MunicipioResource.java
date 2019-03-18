package it.prova.web.rest.resources;

import java.util.HashMap;
import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.prova.model.Municipio;
import it.prova.service.municipio.MunicipioService;
import it.prova.web.dto.municipio.MunicipioDTO;

@Component
@Path("/municipio")
public class MunicipioResource {

	@Autowired
	MunicipioService municipioService;

	@GET
	@Path("{id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMunicipio(@PathParam("id") Long id) {
		return Response.status(200).entity(new MunicipioDTO(municipioService.caricaSingoloMunicipioConAbitanti(id)))
				.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertNuovoMunicipio(Municipio municipioInput) {

		HashMap<String, String> listaErrori = new MunicipioDTO(municipioInput).validate();

		if (!listaErrori.isEmpty()) {
			return Response.status(Response.Status.FORBIDDEN).entity(listaErrori).build();
		}

		municipioService.inserisciNuovo(municipioInput);
		return Response.status(200).entity(municipioInput).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		return Response.status(200).entity(MunicipioDTO.listMuncipioDTO(municipioService.listAllMunicipi())).build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchMunicipio(@QueryParam("descrizione") String descrizione, @QueryParam("codice") String codice,
			@QueryParam("ubicazione") String ubicazione) {
		System.out.println(descrizione + " " + codice + " " + ubicazione);
		List<Municipio> result = municipioService.findByExample(new Municipio(descrizione, codice, ubicazione));
		return Response.status(200).entity(MunicipioDTO.listMuncipioDTO(result)).build();
	}

	@DELETE
	@Path("{id : \\d+}")
	public Response deleteMunicipio(@PathParam("id") Long id) {
		municipioService.rimuovi(municipioService.caricaSingoloMunicipio(id));
		return Response.status(200).entity("Rimossa Persona avente id: " + id).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response aggiornaMunicipio(Municipio municipioInput) {
		municipioService.aggiorna(municipioInput);
		return Response.status(200).entity(municipioInput).build();
	}
}
