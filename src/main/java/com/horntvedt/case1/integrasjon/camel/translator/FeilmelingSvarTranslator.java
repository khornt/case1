package com.horntvedt.case1.integrasjon.camel.translator;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.horntvedt.case1.integrasjon.dto.svar.feil.LagFeilmeldingSvarDto;
import com.horntvedt.case1.integrasjon.dto.svar.feil.ValideringsfeilDto;

//import javax.ws.rs.NotFoundException;

public class FeilmelingSvarTranslator implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeilmelingSvarTranslator.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        setFeilmelingRespons(exchange);
    }

    private void setFeilmelingRespons(Exchange exchange) throws Exception {

        Exception caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        if (caused instanceof PredicateValidationException) {

            LagFeilmeldingSvarDto feil;
            feil = translateTilValideringsFeilmelding(exchange);
            exchange.getIn().setBody(feil);
        }  else {

            LOGGER.error("Feil i feilhåndtering: Feilet i å sette feilmelding");
        }
    }

    private void byggFeilmelding(Exchange exchange, String feilmelding, String feilkode) {

        LagFeilmeldingSvarDto feil = new LagFeilmeldingSvarDto();
        feil.setFeilmelding(feilmelding);
        feil.setFeilkode(feilkode);
        exchange.getIn().setBody(feil);

    }

    private void byggFeilmelding(Exchange exchange, String feilmelding) {

        LagFeilmeldingSvarDto feil = new LagFeilmeldingSvarDto();
        feil.setFeilmelding(feilmelding);
        exchange.getIn().setBody(feil);

    }

    private LagFeilmeldingSvarDto translateTilValideringsFeilmelding(Exchange exchange)
        throws Exception {

        Map<String, String> errorMap = (Map<String, String>) exchange.getIn().getHeader("ValidationErrorMap");

        LagFeilmeldingSvarDto lagFeilmeldingSvarDto =
            new LagFeilmeldingSvarDto();
        lagFeilmeldingSvarDto.setFeilkode("5001");
        lagFeilmeldingSvarDto.setFeilmelding("validering paa forespoersel feilet");
        List<ValideringsfeilDto> valideringsfeilList = new ArrayList<>();
        Optional.ofNullable(errorMap).ifPresent(stringStringMap -> errorMap.forEach((feil, feilmeliding) -> {
            ValideringsfeilDto valideringsFeil = new ValideringsfeilDto();
            valideringsFeil.setFeil(feil);
            valideringsFeil.setFeilmelding(feilmeliding);
            valideringsfeilList.add(valideringsFeil);
        }));

        sjekkSpesifikkException(exchange.getProperties(), valideringsfeilList);

        if (!valideringsfeilList.isEmpty()) {
            lagFeilmeldingSvarDto.setValideringsFeilList(valideringsfeilList);
        }

        return lagFeilmeldingSvarDto;
    }

    private void sjekkSpesifikkException(Map<String, Object> properties,
        List<ValideringsfeilDto> valideringsFeilList) {
        if (properties != null && properties.containsKey("CamelExceptionCaught")) {
            Exception exception = (Exception) properties.get("CamelExceptionCaught");
            if (exception instanceof JsonMappingException) {
                Exception causeException = (Exception) exception.getCause();
                if (causeException instanceof DateTimeParseException) {
                    String cause = exception.getCause().getMessage();
                    ValideringsfeilDto valideringsFeil = new ValideringsfeilDto();
                    valideringsFeil.setFeil("datoformat");
                    valideringsFeil.setFeilmelding(cause);
                    valideringsFeilList.add(valideringsFeil);
                }
            }
        }
    }

}
