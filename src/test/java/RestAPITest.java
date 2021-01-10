import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
class RestAPITest {

    @BeforeAll
    static void setup(){
        RestAssured.baseURI = "https://pokeapi.co";
        RestAssured.proxy("0.0.0.0", 8090, "http");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/rest/pokemons.csv", numLinesToSkip = 1)
    void testPokemonJson(String id, String name, Integer baseExperience, String ability1, String ability2, Integer weight) {
        given().log().uri().
                when().relaxedHTTPSValidation().get("/api/v2/pokemon/" + id + "/").
                then().
                statusCode(200).
                body("name", equalTo(name)).
                body("base_experience", equalTo(baseExperience)).
                body("abilities[0].ability.name", equalTo(ability1)).
                body("abilities[1].ability.name", equalTo(ability2)).
                body("weight", equalTo(weight));
        log.info("Correct info for " + name);
    }
}

