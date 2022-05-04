package ru.learnup.tests.lesson34;

import ru.learnup.dao.CreateTokenRequest;

public class HowToCreateAnObject {
    // 1. используя конструктор
    CreateTokenRequest request = new CreateTokenRequest("admin", "pass");

    // 2. Используя builder
    public static void main(String[] args) {
        CreateTokenRequest request = CreateTokenRequest.builder()
                .password("pass")
                .username("admin")
                .build();


        /////
        // для изменения объекта
        request.withPassword("newPass!!!");

        // 3. для создания объекта
        CreateTokenRequest request1 = new CreateTokenRequest()
                .withUsername("myAccount")
                .withPassword("qwerty");
    }
}
