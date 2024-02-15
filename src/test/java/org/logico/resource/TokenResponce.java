package org.logico.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponce {

    private String token;

    @JsonProperty("accessToken")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
