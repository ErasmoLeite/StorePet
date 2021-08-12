// 1- Pacote
package petstore;


// 2- Biblioteca


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

// 3- Classe
public class Pet {

    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet";   // endereço da entidade Pet

    //3.2  - Metódos e Funções
    public String lerJson(String caminhoJson) throws IOException {

    return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create = Post
    @Test  // Identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");


        //Sintaxe Gherkin
        // Given
        // When
        // Then

    given()
        .contentType("application/json") // Comum em API REST - Antigas era " text/xml"
        .log().all()
        .body(jsonBody)
    .when()   // Quando
        .post(uri)
    .then()
        .log().all()
        .statusCode(200)
    ;

    }
}
