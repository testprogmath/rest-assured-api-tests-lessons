package ru.learnup.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.dao.CreateTokenRequest;
import ru.learnup.dao.CreateTokenResponse;
import ru.learnup.tests.lesson35.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Generate a token")
@Story("Generate a user token")
public class CreateTokenTests extends BaseTest {
    private static CreateTokenRequest request;
    @BeforeAll
    static void beforeSuite() {

        request = CreateTokenRequest.builder()
                .username("admin")
                .password("password123")
                .build();
    }

    @Test
    void createTokenPositiveTest() {
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
