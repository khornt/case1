package com.horntvedt.case1.integrasjon.dto.svar.feil;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LagFeilmeldingSvarDto {

    @JsonProperty("feilkode")
    private String feilkode;
    @JsonProperty("feilmelding")
    private String feilmelding;
    @JsonProperty("valideringsFeil")
    private List<ValideringsfeilDto> valideringsFeilList;

    public String getFeilkode() {
        return feilkode;
    }

    public void setFeilkode(String kode) {
        this.feilkode = kode;
    }

    public String getFeilmelding() {
        return feilmelding;
    }

    public void setFeilmelding(String melding) {
        this.feilmelding = melding;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ValideringsfeilDto> getValideringsFeilList() {
        return valideringsFeilList;
    }

    public void setValideringsFeilList(
        List<ValideringsfeilDto> valideringsFeilList) {
        this.valideringsFeilList = valideringsFeilList;
    }
}
