package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;
import com.horntvedt.case2.fagsystem.kunde.v1.KundeForespoersel;
import com.horntvedt.case2.fagsystem.produkt.v1.ProduktForespoersel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

public class RegisterProduktForespoerselTranslator implements Processor {

    private  static final String KUNDEID = "kundeId";
    private static final String ORIGINAL_MSG = "orginalMelding";

    @Override
    public void process(Exchange exchange) throws Exception {

        String kundeId = exchange.getProperty(KUNDEID, String.class);
        ForespoerselDto origForespoersel = exchange.getProperty(ORIGINAL_MSG, ForespoerselDto.class);

        ProduktForespoersel produktForespoersel = new ProduktForespoersel();

        produktForespoersel.setKundeId(kundeId);
        produktForespoersel.setProduktId(origForespoersel.getProdukt().getProduktId());
        produktForespoersel.setBetingelser(origForespoersel.getProdukt().getBonus());

        exchange.getOut().setBody(produktForespoersel);
        exchange.getOut().setHeader(CxfConstants.OPERATION_NAME, "registrerProdukt");
    }
}
