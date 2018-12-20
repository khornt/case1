package com.horntvedt.case1.integrasjon.camel;

import com.horntvedt.case1.integrasjon.camel.translator.ProduktbestillingSvarTranslator;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.horntvedt.case1.integrasjon.camel.translator.FeilmelingSvarTranslator;
import com.horntvedt.case1.integrasjon.camel.validator.ForespoerselValidator;
import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;

@Component
public class IntegrasjonApiRoute extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrasjonApiRoute.class);

    public void configure() {

        onException(Exception.class)
            .log(LoggingLevel.ERROR, LOGGER, "Det oppstod en uventet feil: ${exception.stacktrace}")
            .maximumRedeliveries(0)
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
            .setBody(constant(null));

        onException(PredicateValidationException.class)
            .log(LoggingLevel.INFO, LOGGER, "Route feilet i predicate sjekk: ${exception.stacktrace}")
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .maximumRedeliveries(0)
            .process(new FeilmelingSvarTranslator());


        restConfiguration().component("servlet").port("8080")
            .bindingMode(RestBindingMode.json).skipBindingOnErrorCode(false);

        rest("/api/v1/avtale").post()
            .type(ForespoerselDto.class)
            .route().routeId("restPost motta bestilling route")
            .validate(new ForespoerselValidator())
            .log(LoggingLevel.INFO, LOGGER, "Melding validert OK")
            .to("direct:opprettKunde")
            .to("direct:opprettProdukt")
            .process(new ProduktbestillingSvarTranslator())
            .end();




        from("direct:opprettKunde")
            .log(LoggingLevel.INFO, LOGGER, "Oppretter kunde i fagsystem");




        from("direct:opprettProdukt")
            .log(LoggingLevel.INFO, LOGGER, "Oppretter produkt i fagsystem");

    }

}
