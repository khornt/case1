package com.horntvedt.case1.integrasjon.dto.forespoersel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "foedselsnummer",
    "organisasjonsnummer",
    "navn" })

public class ForespoerselDto {


    @JsonProperty
    private String foedselsnummer;

    @JsonProperty
    private String organisasjonsnummer;

    @JsonProperty
    private String navn;

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

    @JsonProperty("navn")
    public String getNavn() {
        return navn;
    }

    @JsonProperty("navn")
    public void setNavn(String navn) {
        this.navn = navn;
    }
}
