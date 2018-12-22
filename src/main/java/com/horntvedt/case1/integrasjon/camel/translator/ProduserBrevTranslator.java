package com.horntvedt.case1.integrasjon.camel.translator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProduserBrevTranslator implements Processor {




    @Override
    public void process(Exchange exchange) throws Exception {

        String brevet = "Heisann og velkommen til oss";
        exchange.getIn().setBody(brevet);


    }




}
