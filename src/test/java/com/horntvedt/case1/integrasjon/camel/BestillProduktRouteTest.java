package com.horntvedt.case1.integrasjon.camel;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.horntvedt.case1.integrasjon.TestBase;

public class BestillProduktRouteTest extends TestBase {

    @ClassRule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule(wireMockConfig());
    private String url;

    @Before
    public void createTestSetup() throws Exception {
        url = "http://localhost:" + randomServerPort + "/rs/api/v1/avtale";
    }

    @Test
    public void skalSendeInnProduktbestillingOgFaaOkTilbake() throws Exception {

        String body = getMessage("testdata/bestillProdukt.json");

        //String body = getMessage("testdata/forespoersel.json");

        given()
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .header(HttpHeaders.ACCEPT, "application/json")
            .body(body)
            .when()
            .post(url)
            .then()
            .statusCode(200)
            .assertThat().body(jsonEquals("eplekake").when(IGNORING_ARRAY_ORDER));

    }
}
