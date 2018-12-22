package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case2.fagsystem.kunde.v1.KundeRespons;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RegisterKundeResponsTranslator implements Processor {

    private  static final String KUNDEID = "kundeId";

    @Override
    public void process(Exchange exchange) throws Exception {

        KundeRespons kundeRespons = exchange.getIn().getBody(KundeRespons.class);
        exchange.setProperty(KUNDEID, kundeRespons.getKundeId());
    }
}
