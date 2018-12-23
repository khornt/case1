package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case1.integrasjon.dto.svar.ResponsDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ProduktbestillingSvarTranslator implements Processor {

    private  static final String KUNDEID = "kundeId";
    private  static final String AVTALENUMMER = "avtaleNummer";

    @Override
    public void process(Exchange exchange) throws Exception {

        ResponsDto svar = new ResponsDto();

        svar.setStatus("Ok");
        svar.setKundeNummer(exchange.getProperty(KUNDEID, String.class));
        svar.setAvtaleNummer(exchange.getProperty(AVTALENUMMER, String.class));

        exchange.getOut().setBody(svar);
    }
}
