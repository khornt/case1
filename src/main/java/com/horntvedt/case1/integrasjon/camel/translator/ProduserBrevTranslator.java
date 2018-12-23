package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProduserBrevTranslator implements Processor {


    private static final String ORIGINAL_MSG = "orginalMelding";
    private  static final String KUNDEID = "kundeId";
    private  static final String AVTALENUMMER = "avtaleNummer";

    @Override
    public void process(Exchange exchange) throws Exception {

        ForespoerselDto forespoersel = exchange.getProperty(ORIGINAL_MSG, ForespoerselDto.class);


        String brev = "Hei " + forespoersel.getFornavn() + ". Velkommen til oss \n\n " +
                "Ditt kundenummer er: "  + exchange.getProperty(KUNDEID, String.class);

        exchange.getIn().setBody(brev);

    }

}
