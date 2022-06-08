package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String photo;

    private String mail;

    private Instant dateDeNaissance;

    private String motDePasse;

    private EntrepriseDto entreprise;

    private List<RolesDto> roles;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if (utilisateur == null){
            return null;
            // TODO throw exception
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .photo(utilisateur.getPhoto())
                .mail(utilisateur.getMail())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(utilisateur.getRoles() != null ?
                        utilisateur.getRoles().stream()
                                .map(RolesDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){
        if (utilisateurDto == null){
            return null;
            // TODO throw exception
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        utilisateur.setRoles(utilisateurDto.getRoles() != null ?
                        utilisateurDto.getRoles().stream()
                                .map(RolesDto::toEntity)
                                .collect(Collectors.toList()) : null

                );

        return utilisateur;
    }
}
