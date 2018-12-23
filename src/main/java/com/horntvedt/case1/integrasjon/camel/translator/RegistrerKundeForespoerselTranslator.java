package com.horntvedt.case1.integrasjon.camel.translator;

import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;
import com.horntvedt.case2.fagsystem.aktor.v1.Aktor;
import com.horntvedt.case2.fagsystem.kunde.v1.KundeForespoersel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

public class RegistrerKundeForespoerselTranslator implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {

        KundeForespoersel kundeForespoersel = lagKundeforespoersel(exchange);

        exchange.setProperty("origianForespoersel", exchange.getIn().getBody(ForespoerselDto.class));
        exchange.getOut().setBody(kundeForespoersel);
        exchange.getOut().setHeader(CxfConstants.OPERATION_NAME, "registrerKunde");
    }


    private KundeForespoersel lagKundeforespoersel(Exchange exchange) {

        ForespoerselDto forespoersel = exchange.getIn().getBody(ForespoerselDto.class);

        KundeForespoersel kundeForespoersel = new KundeForespoersel();
        Aktor aktor = new Aktor();

        if (!sjekkBlankOgNull(forespoersel.getFoedselsnummer())) {  //todo?
            aktor.setFoedselsnummer(forespoersel.getFoedselsnummer());

        } else {
            aktor.setOrganisasjonsnummer(forespoersel.getOrganisasjonsnummer());
        }

        settNavn(aktor, forespoersel);
        settAdresse(aktor, forespoersel);
        kundeForespoersel.setKundeInfo(aktor);

        return kundeForespoersel;
    }


    private Aktor settNavn(Aktor aktor, ForespoerselDto forespoersel) {

        aktor.setFornavn(forespoersel.getFornavn());
        aktor.setMellomnavn(forespoersel.getMellomnavn());
        aktor.setEtternavn(forespoersel.getEtternavn());

        return aktor;
    }

    private Aktor settAdresse(Aktor aktor, ForespoerselDto forespoerselDto) {

        aktor.setAdresselinje1(forespoerselDto.getAdresse().getAdresselinje1());
        aktor.setAdresselinje2(forespoerselDto.getAdresse().getAdresselinje2());
        aktor.setPostnummer(forespoerselDto.getAdresse().getPostnummer());
        aktor.setPoststed(forespoerselDto.getAdresse().getPoststed());

        return aktor;
    }

    private boolean sjekkBlankOgNull(String value) {

        if ((value != null) && (!"".equals(value))) {
            return false;
        }
        return true;
    }
}
