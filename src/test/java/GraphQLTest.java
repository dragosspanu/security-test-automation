import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utils.GraphQLUtil;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
class GraphQLTest {

    @BeforeAll
    static void setup(){
        RestAssured.baseURI = "https://countries.trevorblades.com";
        RestAssured.proxy("0.0.0.0", 8090, "http");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/graphql/countries.csv", numLinesToSkip = 1)
    void testGraphQL(String code, String name, String capital, String continent) {
        Map<String, String> variables = new HashMap<>();
        variables.put("code", code);
        String graphqlPayload = GraphQLUtil.prepareGraphqlPayload(variables, "src/test/resources/graphql/countries.graphql");
        given().log().body()
                .contentType(ContentType.JSON)
                .body(graphqlPayload).relaxedHTTPSValidation()
                .when()
                .post("/graphql")
                .then()
                .statusCode(200)
                .body("data.country.name", equalTo(name))
                .body("data.country.capital", equalTo(capital))
                .body("data.country.continent.name", equalTo(continent));
        log.info("GraphQL request successful for " + code);
    }
}
