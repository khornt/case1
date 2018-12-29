package com.horntvedt.case1.integrasjon.camel.transaltors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horntvedt.case1.integrasjon.camel.translator.RegistrerKundeForespoerselTranslator;
import com.horntvedt.case1.integrasjon.dto.forespoersel.ForespoerselDto;
import com.horntvedt.case2.fagsystem.kunde.v1.KundeForespoersel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RegistrereKundeForespoerselTranslatorTest {


    protected static String getMessage(final String fileName) throws IOException {
        File responseMessage = new ClassPathResource(fileName).getFile();
        return FileUtils.readFileToString(responseMessage, "utf-8");
    }

    @Test
    public void skalTaImotForespoerselDtoOgLageEtKomplettKundeforespoerselObjektTest() throws Exception {


        String melding = getMessage("testdata/bestillProdukt.json");
        ObjectMapper objectMapper = new ObjectMapper();
        ForespoerselDto forespoersel = objectMapper.readValue(melding, ForespoerselDto.class);

        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        Processor process = new RegistrerKundeForespoerselTranslator();

        exchange.getIn().setBody(forespoersel, ForespoerselDto.class);
        process.process(exchange);

        KundeForespoersel kundeforespoersel = exchange.getOut().getBody(KundeForespoersel.class);

        assertThat("01019933544", equalTo(kundeforespoersel.getKundeInfo().getFoedselsnummer()));
        assertThat("Kristian", equalTo(kundeforespoersel.getKundeInfo().getFornavn()));
        assertThat("CamelRider", equalTo(kundeforespoersel.getKundeInfo().getMellomnavn()));
        assertThat("Oslo", equalTo(kundeforespoersel.getKundeInfo().getPoststed()));
        assertThat("1001", equalTo(forespoersel.getProdukt().getProduktId()));

    }
}
