package com.sofka.alphapostcomments.application.config.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProvider {

    private String secretKey = "this-is-my-secret-key-it-has-got-to-be-a-lot-longer";
    private long tokenValidity = 360000;

}
