package com.abdproject.gestionstock.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthentificationRequest {

    private String login;

    private String password;
}
