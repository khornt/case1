package com.horntvedt.case1.integrasjon.camel.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horntvedt.case1.integrasjon.dto.svar.ResponsDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ProduktbestillingSvarTranslator implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {

        ResponsDto svar = new ResponsDto();

        svar.setOrdrenummer("123456789");
        svar.setStatus("Ok");

        ObjectMapper mapper = new ObjectMapper();

        //json ti string bør være unødvendig, bør fungere direkte
        exchange.getIn().setBody(mapper.writeValueAsString(svar));
    }
}
