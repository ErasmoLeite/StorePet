// 1 - Pacote
package datadriven;

// 3 - Import

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


// 3.1 -  Atributos
public class UserDD {

        String uri = "https://petstore.swagger.io/v2/user";
        Data data ; // Objeto que representa a classe utils.Date

        //3.2  - Metódos e Funções

    @DataProvider
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> testCases = new ArrayList<>();   // Salvar todos os testes cases
        //List<String[]> testCases = new ArrayList<>();   // Salvar todos os testes cases
        String[] testCase ;  // Salvar 1 teste de cada vez
        String linha;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/users.csv"));
        while ((linha = bufferedReader.readLine()) != null) {
            testCase = linha.split(",");  // informar o separador das colunas
            testCases.add(testCase);

        }
            return testCases.iterator();
    }

    @BeforeMethod
    public void setup() {
        data  = new Data();

    }
        @Test(dataProvider = "provider")
        public void incluirUsuario(
                String id,
                String username,
                String firtsName,
                String lastName,
                String email,
                String password,
                String phone,
                String userStatus) throws IOException {

            String jsonBody = new JSONObject()
            .put("id",id)
            .put("username", username )
            .put("firstName", firtsName )
            .put("lastName", lastName)
            .put("email", email )
            .put("password", password)
            .put("phone", phone)
            .put("userStatus",userStatus)
                .toString();

            String userId =
                    given()
                            .contentType("application/json")
                            .log().all()
                            .body(jsonBody)
                            .when()
                            .post(uri)
                            .then()
                            .log().all()
                            .statusCode(200)
                            .body("code", is(200))
                            .body("type", is("unknown"))
                            .extract()
                            .path("message")
                    ;

            System.out.println("O userID é " + userId);
        }

    }
