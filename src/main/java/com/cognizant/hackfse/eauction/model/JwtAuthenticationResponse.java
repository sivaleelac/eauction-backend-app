package com.cognizant.hackfse.eauction.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;
    private String emailAddress;
    private String classification;
    private final  String tokenType = "Bearer";

}
