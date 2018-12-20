package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case1.integrasjon.dto.svar.ResponsDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProduktbestillingSvarTranslator implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {

        ResponsDto svar = new ResponsDto();

        svar.setOrdrenummer("123456789");
        svar.setStatus("Ok");

        exchange.getIn().setBody(svar);
    }

}
