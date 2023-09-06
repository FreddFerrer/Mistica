package com.mistica.EducarTransformar.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String rol;

    public JwtResponse(String accessToken, Long id, String username, String email, String rol) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
    }
}
