package it.prova.web.dto.abitante;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.model.Abitante;
import it.prova.web.dto.municipio.MunicipioDTO;

public class AbitanteDTO {
	private Long id;
	private String nome;
	private String cognome;
	private Integer eta;
	private String residenza;
	@JsonIgnoreProperties(value = { "abitanti" })
	private MunicipioDTO municipio;

	public AbitanteDTO() {

	}

	public AbitanteDTO(Abitante source) {
		this.id = source.getId();
		this.nome = source.getNome();
		this.cognome = source.getCognome();
		this.eta = source.getEta();
		this.residenza = source.getResidenza();
		this.municipio = MunicipioDTO.buildMunicipioDTO(source.getMunicipio());
	}

	public AbitanteDTO(String nome, String cognome, Integer eta, String residenza, MunicipioDTO municipio) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.eta = eta;
		this.residenza = residenza;
		this.municipio = municipio;
	}

	public static Set<AbitanteDTO> listAbitanteDTO(List<Abitante> listaAbitanti) {
		Set<AbitanteDTO> setAbitantiDTO = new HashSet<>();
		listaAbitanti.forEach(abitante -> setAbitantiDTO.add(new AbitanteDTO(abitante)));
		return setAbitantiDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Integer getEta() {
		return eta;
	}

	public void setEta(Integer eta) {
		this.eta = eta;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public MunicipioDTO getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioDTO municipio) {
		this.municipio = municipio;
	}

	public HashMap<String, String> validate() {

		HashMap<String, String> messagesList = new HashMap<>();

		if (StringUtils.isEmpty(nome)) {
			messagesList.put("Descrizione", "Campo obbligatorio!");
		}

		if (StringUtils.isEmpty(cognome)) {
			messagesList.put("Codice", "Campo obbligatorio!");
		}

		if (StringUtils.isEmpty(eta + "")) {
			messagesList.put("Codice", "Campo obbligatorio!");
		}

		if (StringUtils.isEmpty(residenza)) {
			messagesList.put("Residenza", "Campo obbligatorio!");
		}

		return messagesList;
	}
}
