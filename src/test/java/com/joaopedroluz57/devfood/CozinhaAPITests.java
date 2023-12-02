package com.joaopedroluz57.devfood;

import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import com.joaopedroluz57.devfood.util.DatabaseCleaner;
import com.joaopedroluz57.devfood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
public class CozinhaAPITests {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private static final int ID_COZINHA_INEXISTENTE = 100;

    private Cozinha cozinhaBrasileira;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaItaliana;


    @Before
    public void setUp() {
        RestAssured.port = port;
        basePath = "/cozinhas";
        enableLoggingOfRequestAndResponseIfValidationFails();

        jsonCorretoCozinhaItaliana = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-italiana.json");

        databaseCleaner.clearTables();
        prepararCozinha();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(quantidadeCozinhasCadastradas))
                .body("nome", hasItems(cozinhaBrasileira.getNome()));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinhaComDadosCorretos() {
        given()
                .body(jsonCorretoCozinhaItaliana)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRepostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", cozinhaBrasileira.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinhaBrasileira.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", ID_COZINHA_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararCozinha() {
        cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaChinesa = new Cozinha();
        cozinhaChinesa.setNome("Chinesa");
        cozinhaRepository.save(cozinhaChinesa);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

}
