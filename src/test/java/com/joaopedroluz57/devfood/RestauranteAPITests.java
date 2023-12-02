package com.joaopedroluz57.devfood;

import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
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

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
public class RestauranteAPITests {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private static final int ID_RESTAURANTE_INEXISTENTE = 100;

    private int quantidadeRestaurantesCadastrados;
    private String jsonCorretoBarDaOrla;
    private Restaurante restauranteDoSeuZe;


    @Before
    public void setUp() {
        RestAssured.port = port;
        basePath = "/restaurantes";
        enableLoggingOfRequestAndResponseIfValidationFails();

        jsonCorretoBarDaOrla = ResourceUtils
                .getContentFromResource("/json/correto/restaurante.json");

        databaseCleaner.clearTables();
        prepararRestaurantes();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarQuantidadeCorretaDeRestaurantes_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(quantidadeRestaurantesCadastrados))
                .body("nome", hasItems(restauranteDoSeuZe.getNome()));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestauranteComDadosCorretos() {
        given()
                .body(jsonCorretoBarDaOrla)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRepostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", restauranteDoSeuZe.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(restauranteDoSeuZe.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", ID_RESTAURANTE_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararRestaurantes() {
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        restauranteDoSeuZe = new Restaurante();
        restauranteDoSeuZe.setNome("Restaurante do Seu Zé");
        restauranteDoSeuZe.setCozinha(cozinhaBrasileira);
        restauranteDoSeuZe.setTaxaEntrega(BigDecimal.TEN);
        restauranteRepository.save(restauranteDoSeuZe);

        restauranteDoSeuZe = new Restaurante();
        restauranteDoSeuZe.setNome("Restaurante da Dona Maria");
        restauranteDoSeuZe.setCozinha(cozinhaBrasileira);
        restauranteDoSeuZe.setTaxaEntrega(BigDecimal.ONE);
        restauranteRepository.save(restauranteDoSeuZe);

        quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
    }

}
