package com.horntvedt.case1.integrasjon.dto.forespoersel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "foedselsnummer",
    "organisasjonsnummer",
    "fornavn",
    "mellomnavn",
    "etternavn",
    "adresse",
    "produkt"


})

public class ForespoerselDto {


    @JsonProperty
    private String foedselsnummer;

    @JsonProperty
    private String organisasjonsnummer;

    @JsonProperty
    private String fornavn;

    @JsonProperty
    private String mellomnavn;

    @JsonProperty
    private String etternavn;

    @JsonProperty
    private AdresseDto adresse;

    @JsonProperty
    private ProduktDto produkt;

    @JsonProperty("foedselsnummer")
    public String getFoedselsnummer() {
        return foedselsnummer;
    }

    @JsonProperty("foedselsnummer")
    public void setFoedselsnummer(String foedselsnummer) {
        this.foedselsnummer = foedselsnummer;
    }

    @JsonProperty("organisasjonsnummer")
    public String getOrganisasjonsnummer() {
        return organisasjonsnummer;
    }

    @JsonProperty("organisasjonsnummer")
    public void setOrganisasjonsnummer(String organisasjonsnummer) {
        this.organisasjonsnummer = organisasjonsnummer;
    }


    @JsonProperty("fornavn")
    public String getFornavn() {
        return fornavn;
    }

    @JsonProperty("fornavn")
    public void setFornavn(String navn) {
        this.fornavn = navn;
    }

    @JsonProperty("mellomnavn")
    public String getMellomnavn() {
        return mellomnavn;
    }

    @JsonProperty("mellomnavn")
    public void setMellomnavn(String navn) {
        this.mellomnavn = navn;
    }

    @JsonProperty("etternavn")
    public String getEtternavn() {
        return etternavn;
    }

    @JsonProperty("etternavn")
    public void setEtternavn(String navn) {
        this.etternavn = navn;
    }

    @JsonProperty("adresse")
    public AdresseDto getAdresse() {
        return adresse;
    }

    @JsonProperty("adresse")
    public void setAdresse(AdresseDto adresse) {
        this.adresse = adresse;
    }


    @JsonProperty("produkt")
    public ProduktDto getProdukt() {
        return produkt;
    }

    @JsonProperty("produkt")
    public void setProdukt(ProduktDto produkt) {
        this.produkt = produkt;
    }

}
