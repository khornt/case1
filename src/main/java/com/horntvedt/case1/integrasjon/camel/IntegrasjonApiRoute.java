package com.horntvedt.case1.integrasjon.camel;

import com.horntvedt.case1.integrasjon.camel.translator.*;
import com.horntvedt.case1.integrasjon.camel.validator.ForespoerselValidator;
import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;
import com.horntvedt.case1.integrasjon.dto.svar.ResponsDto;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IntegrasjonApiRoute extends RouteBuilder {


    @Value("${fag.url}")
    private String fagUrl;

    @Value("${fag.port}")
    private String fagPort;

    @Value("${amq.utpostkasse}")
    private String utpostkasse;

    private String fagsystemEndepunkt() {

        return "cxf:/fagsystem?"
            + "address=" + fagUrl + ":" + fagPort + "/soap-api/fagsystem/v1/fagsystem"
            + "&serviceClass=com.horntvedt.case2.fagsystem.v1.Fagsystem"
            + "&serviceName={urn:com:horntvedt:case2:fagsystem:v1}fagsystem"
            + "&skipFaultLogging=false"
            + "&loggingFeatureEnabled=true"
            + "&allowStreaming=false";

    }

    private static final String ORIGINAL_MSG = "orginalMelding";
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrasjonApiRoute.class);

    public void configure() {


        onException(Exception.class)
            .log(LoggingLevel.ERROR, LOGGER, "Det oppstod en uventet feil: ${exception.stacktrace}")
            .maximumRedeliveries(0)
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
            .process(new FeilmelingSvarTranslator());
            //.setBody(constant("{\"feil\":\"Dette gikk skeis\"}"));

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
            .outType(ResponsDto.class)  //todo: nødvendig??
            .route().routeId("restPost motta bestilling route")
            .validate(new ForespoerselValidator())
            .setProperty(ORIGINAL_MSG, simple("in.body"))
            .log(LoggingLevel.INFO, LOGGER, "Melding validert OK")
            .to("direct:opprettKunde")
            .removeHeaders("*")
            .to("direct:opprettProdukt")
             .to("direct:sendBrevTilKoe")
            .process(new ProduktbestillingSvarTranslator())

            .end();


        from("direct:opprettKunde")
            .log(LoggingLevel.INFO, LOGGER, "Oppretter kunde i fagsystem")
            .process(new RegistrerKundeForespoerselTranslator())
            .to(fagsystemEndepunkt()).routeId("Soap kall mot fagsystem: registrerKunde")
            .process(new RegisterKundeResponsTranslator())
            .log(LoggingLevel.INFO, LOGGER, "Utført soap kall mot fagsystem, operation: : registrerKunde");


        from("direct:opprettProdukt")
            .log(LoggingLevel.INFO, LOGGER, "Oppretter produkt i fagsystem: registrerProdukt")
            .process(new RegisterProduktForespoerselTranslator())
            .to(fagsystemEndepunkt()).routeId("Soap kall mot fagsystem: registrerProdukt")
            .process(new RegistrerProduktResponsTranslator())
            .log(LoggingLevel.INFO, LOGGER, "Utført soap kall mot fagsystem, operation: registerProdukt");


        from("direct:sendBrevTilKoe").routeId("Send brev til brevkø")
                .log(LoggingLevel.INFO, LOGGER, "Sender ut brev")
                .setExchangePattern(ExchangePattern.InOnly)
                .process(new ProduserBrevTranslator())
                .to("activemq:queue:" + utpostkasse + "?jmsMessageType=Text");


    }
}
