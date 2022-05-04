package ru.learnup.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@With
@ToString
public class CreateTokenRequest {

    public CreateTokenRequest( String username,  String password) {
        this.username = username;
        this.password = password;
    }

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public CreateTokenRequest() {
    }
}

/*
{
    "username" : "admin",
    "password" : null
}
как правило, это тоже самое, что
{
    "username" : "admin"
}
 */