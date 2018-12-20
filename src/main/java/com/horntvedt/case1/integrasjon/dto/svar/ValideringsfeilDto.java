package com.horntvedt.case1.integrasjon.dto.svar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValideringsfeilDto {

    @JsonProperty("feil")
    private String feil;

    @JsonProperty("feilmelding")
    private String feilmelding;


    public String getFeil() {
        return feil;
    }

    public void setFeil(String feil) {
        this.feil = feil;
    }

    public String getFeilmelding() {
        return feilmelding;
    }

    public void setFeilmelding(String feilmelding) {
        this.feilmelding = feilmelding;
    }
}
