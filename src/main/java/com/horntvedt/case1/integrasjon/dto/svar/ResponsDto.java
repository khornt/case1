package com.horntvedt.case1.integrasjon.dto.svar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

    "status",
    "kundeNummer",
    "avtaleNummer"
})

public class ResponsDto {

    @JsonProperty
    private String status;

    @JsonProperty
    private String kundeNummer;

    @JsonProperty
    private String avtaleNummer;


    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }


    @JsonProperty("kundeNummer")
    public String getKundeNummer() {
        return kundeNummer;
    }

    @JsonProperty("kundeNummer")
    public void setKundeNummer(String kundeNummer) {
        this.kundeNummer = kundeNummer;
    }


    @JsonProperty("avtaleNummer")
    public String getAvtaleNummer() {
        return avtaleNummer;
    }

    @JsonProperty("avtaleNummer")
    public void setAvtaleNummer(String avtaleNummer) {
        this.avtaleNummer = avtaleNummer;
    }

}
