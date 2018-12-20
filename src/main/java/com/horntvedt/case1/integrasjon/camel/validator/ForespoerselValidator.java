package com.horntvedt.case1.integrasjon.camel.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;

@Component
public class ForespoerselValidator implements Predicate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForespoerselValidator.class);

    private static final String FOEDSELSNUMMER_FORMAT = "\\d{11}";
    private static final String ORGANISASJONSNUMMER_FORMAT = "\\d{9}";

    @Override
    public boolean matches(Exchange exchange) {

        Map<String, String> errorMap = new HashMap<>(); //todo: begynner med en feil, tar høyde for mange
        ForespoerselDto forespoersel = exchange.getIn().getBody(ForespoerselDto.class);
        validerForespoersel(forespoersel.getFoedselsnummer(), forespoersel.getOrganisasjonsnummer(), errorMap);

        if (errorMap.size() > 0) {
            LOGGER.warn("forespoersel er ikke gyldig");

            //todo: hvorfor ligger denne på header og ikke en exchange property?
            exchange.getIn().setHeader("validationErrorMap", errorMap);
            return false;
        }
        return true;
    }

    private void validerForespoersel(String fnr, String orgnr, Map<String, String> errorMap) {

        if (!sjekkBlankOgNull(fnr)) {

            if (!fnr.matches(FOEDSELSNUMMER_FORMAT)) {
                errorMap.put("formatfeil", "foedselsnummer format ugyldig");
                LOGGER.warn("Fødselsnummeret er i feil format");
            }
        } else if (!sjekkBlankOgNull(orgnr)) {
            if (!orgnr.matches(ORGANISASJONSNUMMER_FORMAT)) {
                errorMap.put("formatfeil", "orgnisasjonsnummer format ugyldig");
                LOGGER.warn("Organisasjonsnummeret er i feil format");
            }
        }
    }

    private boolean sjekkBlankOgNull(String value) {

        if ((value != null) && (!"".equals(value))) {
            return false;
        }

        return true;
    }
}
