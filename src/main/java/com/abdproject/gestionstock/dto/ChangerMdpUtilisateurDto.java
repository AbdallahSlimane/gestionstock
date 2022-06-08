package com.abdproject.gestionstock.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangerMdpUtilisateurDto {

    private Integer id;

    private String motDePasse;

    private String confirmMotDePasse;
}
