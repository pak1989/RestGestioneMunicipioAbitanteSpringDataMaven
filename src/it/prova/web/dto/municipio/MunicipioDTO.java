package it.prova.web.dto.municipio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.model.Abitante;
import it.prova.model.Municipio;
import it.prova.web.dto.abitante.AbitanteDTO;

public class MunicipioDTO {
	private Long id;
	private String descrizione;
	private String codice;
	private String ubicazione;
	@JsonIgnoreProperties(value = { "municipio" })
	private Set<AbitanteDTO> abitanti = new HashSet<>();

	public MunicipioDTO() {

	}

	public MunicipioDTO(Municipio source) {
		this.id = source.getId();
		this.descrizione = source.getDescrizione();
		this.codice = source.getCodice();
		this.ubicazione = source.getUbicazione();
		for (Abitante abitanteItem : source.getAbitanti()) {
			this.abitanti.add(new AbitanteDTO(abitanteItem));
		}
	}

	public static Set<MunicipioDTO> listMuncipioDTO(List<Municipio> listaMunicipi) {
		Set<MunicipioDTO> setMunicipiDTO = new HashSet<>();
		listaMunicipi.forEach(municipio -> setMunicipiDTO.add(new MunicipioDTO(municipio)));
		return setMunicipiDTO;
	}

	public static MunicipioDTO buildMunicipioDTO(Municipio municipioInput) {
		return new MunicipioDTO(municipioInput.getId(), municipioInput.getDescrizione(), municipioInput.getCodice(),
				municipioInput.getUbicazione());
	}

	public MunicipioDTO(String descrizione, String codice, String ubicazione) {
		super();
		this.descrizione = descrizione;
		this.codice = codice;
		this.ubicazione = ubicazione;
	}

	public MunicipioDTO(Long id, String descrizione, String codice, String ubicazione) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
		this.ubicazione = ubicazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getUbicazione() {
		return ubicazione;
	}

	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}

	public Set<AbitanteDTO> getAbitanti() {
		return abitanti;
	}

	public void setAbitanti(Set<AbitanteDTO> abitanti) {
		this.abitanti = abitanti;
	}

	public HashMap<String, String> validate() {

		HashMap<String, String> messagesList = new HashMap<>();

		if (StringUtils.isEmpty(descrizione)) {
			messagesList.put("Descrizione", "Campo obbligatorio!");
		}

		if (StringUtils.isEmpty(codice)) {
			messagesList.put("Codice", "Campo obbligatorio!");
		}

		if (StringUtils.isEmpty(ubicazione)) {
			messagesList.put("Ubicazione", "Campo obbligatorio!");
		}

		return messagesList;
	}
}
