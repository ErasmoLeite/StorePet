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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

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

            // Validação dos Campos Retorno
        .body("name", is("TesteQA"))
        .body("status", is("available"))
        .body("category.name" , is("dog"))
    ;

    }
    @Test(priority=2)
    public void consultarPet() {
        String petid = "74859674859674";
        String token =
                given()
                        .contentType("application/json")
                        .log().all()
                        .when()
                        .get(uri + "/" + petid)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("category.name", is("dog"))
                        .body("status", is("available"))
                        .extract()
                        .path("category.name");
        System.out.println("o Token é " + token);

    }

    @Test
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
        .contentType("application/json")
        .log().all()
        .body(jsonBody)
        .when()
        .put(uri)
        .then()
        .log().all()
        .statusCode(200)
        .body("name", is("TesteQA"))
        .body("status", is("sold"))
        ;
    }
    @Test (priority = 4)
    public void excluirPet(){
    String petid = "74859674859674";

    given()
        .contentType("application/json")
        .log().all()
    .when()
        .delete(uri + "/" + petid)
        .then()
        .log().all()
         .statusCode(200)
        .body("code", is (200))
        .body("type", is ("unknown"))
    .body("message", is (petid))

;

    }

}
