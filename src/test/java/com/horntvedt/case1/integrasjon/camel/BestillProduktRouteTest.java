package com.horntvedt.case1.integrasjon.camel;

import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.horntvedt.case1.integrasjon.TestBase;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class BestillProduktRouteTest extends TestBase {

    @ClassRule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule(wireMockConfig()
            .notifier(new Slf4jNotifier(true)));
    private String url;

    @Before
    public void createTestSetup() throws Exception {
        url = "http://localhost:" + randomServerPort + "/rs/api/v1/avtale";
    }

    @Test
    public void skalSendeInnProduktbestillingOgFaaOkTilbake() throws Exception {

        String bodyForespoersel = getMessage("testdata/bestillProdukt.json");
        String bodyRespons = getMessage("testdata/bestillingOk.json");

        given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(bodyForespoersel)
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .log().all()
                .assertThat().body(jsonEquals(bodyRespons).when(IGNORING_ARRAY_ORDER));

    }

    @Test
    public void skalSendeInnProduktbestillingOgFaa400ValideringsfeilTilbake() throws Exception {

        String bodyForespoersel = getMessage("testdata/bestillProduktMedFeil.json");
        String bodyRespons = getMessage("testdata/valideringsfeilRespons.json");

        given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(bodyForespoersel)
                .when()
                .post(url)
                .then()
                .statusCode(400)
                .log().all()
                .assertThat().body(jsonEquals(bodyRespons).when(IGNORING_ARRAY_ORDER));

    }
}
