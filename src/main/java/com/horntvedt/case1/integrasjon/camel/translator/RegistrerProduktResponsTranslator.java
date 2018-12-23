package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case2.fagsystem.produkt.v1.ProduktRespons;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RegistrerProduktResponsTranslator implements Processor {

    private static final String AVTALENUMMER = "avtaleNummer";

    @Override
    public void process(Exchange exchange) throws Exception {

        ProduktRespons produktRespons = exchange.getIn().getBody(ProduktRespons.class);
        exchange.setProperty(AVTALENUMMER, produktRespons.getAvtalenummer());

    }
}