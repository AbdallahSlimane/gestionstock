package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.Entreprise;
import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class EntrepriseDto {

    private Integer id;

    private String nom;

    private String description;

    private AdresseDto adresse;

    private String codeFiscal;

    private String photo;

    private String mail;

    private String numTel;

    private String siteweb;

    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise){
        if (entreprise == null){
            return null;
            // TODO throw exception
        }

        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .photo(entreprise.getPhoto())
                .mail(entreprise.getPhoto())
                .numTel(entreprise.getNumTel())
                .codeFiscal(entreprise.getCodeFiscal())
                .siteweb(entreprise.getSiteweb())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .utilisateurs(entreprise.getUtilisateurs() != null ?
                                entreprise.getUtilisateurs().stream()
                                        .map(UtilisateurDto::fromEntity)
                                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto){
        if (entrepriseDto == null){
            return null;
            // TODO throw exception
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setMail(entrepriseDto.getMail());
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setSiteweb(entrepriseDto.getSiteweb());
        entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
        entreprise.setUtilisateurs(entrepriseDto.getUtilisateurs() != null ?
                            entrepriseDto.getUtilisateurs().stream()
                                    .map(UtilisateurDto::toEntity)
                                    .collect(Collectors.toList()) : null

                );

        return entreprise;
    }
}
