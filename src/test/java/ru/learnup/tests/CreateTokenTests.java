package ru.learnup.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.learnup.dao.CreateTokenRequest;
import ru.learnup.dao.CreateTokenResponse;
import ru.learnup.tests.lesson35.BaseTest;
import ru.learnup.tests.lesson36.Lesson36;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Generate a token")
@Story("Generate a user token")
public class CreateTokenTests extends BaseTest {
    final static Logger log = LoggerFactory.getLogger(CreateTokenTests.class);

    private static CreateTokenRequest request;
    @BeforeAll
    static void beforeSuite() {

        request = CreateTokenRequest.builder()
                .username("admin")
                .password("password123")
                .build();
        log.info(request.toString());
    }

    @Test
    void createTokenPositiveTest() {
        log.info("createTokenPositiveTest начался");
        CreateTokenResponse response = given()//предусловия, подготовка
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .header("Content-Type", "application/json")
                .body(request)
                .expect()
                .statusCode(200)
                .when()
                .post("/auth")//шаг(и)
                .prettyPeek() // логируем ответ
                .then()
                .extract()
                .as(CreateTokenResponse.class);
        assertThat(response.getToken().length(), equalTo(15));
        log.info("Тест закончен");
    }

    @Test
    @Description("Create a token with a wrong password")
    void createTokenWithAWrongPasswordNegativeTest() {
        given() //предусловия, подготовка
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .header("Content-Type", "application/json")
                .body(request)
                .when()
                .post("auth")//шаг(и)
                .prettyPeek()
                .then() //проверки
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    void createTokenWithAWrongUsernameAndPasswordNegativeTest() {
        // "тест начался"
        Response response = given() //предусловия, подготовка
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .header("Content-Type", "application/json")
                .body(request.withUsername("admin1233333"))
                .when()
                .post("https://restful-booker.herokuapp.com/auth")//шаг(и)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().jsonPath().get("reason"), containsStringIgnoringCase("Bad credentials"));
    }
}
