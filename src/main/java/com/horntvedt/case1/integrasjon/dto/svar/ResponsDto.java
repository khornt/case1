package com.horntvedt.case1.integrasjon.dto.svar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ordrenummer",
    "status" })

public class ResponsDto {


    @JsonProperty
    private String ordrenummer;

    @JsonProperty
    private String status;


    @JsonProperty("ordrenummer")
    public String getFoedselsnummer() {
        return ordrenummer;
    }

    @JsonProperty("ordrenummer")
    public void setOrdrenummer(String ordrenummer) {
        this.ordrenummer = ordrenummer;
    }


    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }
    
}
