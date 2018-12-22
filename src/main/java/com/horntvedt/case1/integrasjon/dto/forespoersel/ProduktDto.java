package com.horntvedt.case1.integrasjon.dto.forespoersel;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

        "produktId",
        "bonus"
})

public class ProduktDto {

    @JsonProperty
    private String produktId;

    @JsonProperty
    private String bonus;


    @JsonProperty("produktId")
    public String getProduktId() {
        return produktId;
    }

    @JsonProperty("produktId")
    public void setProduktId(String produktId) {
        this.produktId = produktId;
    }

    @JsonProperty("bonus")
    public String getBonus() {
        return bonus;
    }

    @JsonProperty("bonus")
    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
