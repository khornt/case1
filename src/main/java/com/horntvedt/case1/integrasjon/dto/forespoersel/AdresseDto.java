package com.horntvedt.case1.integrasjon.dto.forespoersel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "adresselinje1",
        "adresselinje2",
        "postnummer",
        "poststed"
})

public class AdresseDto {


    @JsonProperty
    private String adresselinje1;

    @JsonProperty
    private String adresselinje2;

    @JsonProperty
    private String poststed;

    @JsonProperty
    private String postnummer;


    @JsonProperty("adresselinje1")
    public String getAdresselinje1() {
        return adresselinje1;
    }
    @JsonProperty("adresselinje1")
    public void setAdresselinje1(String adresselinje1) {
        this.adresselinje1 = adresselinje1;
    }

    @JsonProperty("adresselinje2")
    public String getAdresselinje2() {
        return adresselinje2;
    }

    @JsonProperty("adresselinje2")
    public void setAdresselinje2(String adresselinje2) {
        this.adresselinje2 = adresselinje2;
    }

    @JsonProperty("poststed")
    public String getPoststed() {
        return poststed;
    }

    @JsonProperty("poststed")
    public void setPoststed(String poststed) {
        this.poststed = poststed;
    }

    @JsonProperty("postnummer")
    public String getPostnummer() {
        return postnummer;
    }

    @JsonProperty("postnummer")
    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }
}
