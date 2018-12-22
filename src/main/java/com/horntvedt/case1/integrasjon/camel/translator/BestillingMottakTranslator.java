package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class BestillingMottakTranslator implements Processor {

    private static final String ORIGINAL_MSG = "orginalMelding";

    @Override
    public void process(Exchange exchange) throws Exception {

        //todo: Dette kan fint gjøres rett i routa
        ForespoerselDto forespoersel = exchange.getIn().getBody(ForespoerselDto.class);
        exchange.setProperty(ORIGINAL_MSG, forespoersel);
    }
}
