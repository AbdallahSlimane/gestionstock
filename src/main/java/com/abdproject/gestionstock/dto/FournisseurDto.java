package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.Fournisseur;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FournisseurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String photo;

    private Integer identreprise;

    private String mail;

    private String numTel;

    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if (fournisseur == null){
            return null;
            // TODO throw exception
        }
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .photo(fournisseur.getPhoto())
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getNumTel())
                .identreprise(fournisseur.getIdentreprise())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .commandeFournisseurs(fournisseur.getCommandeFournisseurs() != null ?
                        fournisseur.getCommandeFournisseurs().stream()
                                .map(CommandeFournisseurDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if (fournisseurDto == null){
            return null;
            // TODO throw exception
        }

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setIdentreprise(fournisseurDto.getIdentreprise());
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setAdresse(AdresseDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setCommandeFournisseurs(fournisseurDto.getCommandeFournisseurs() != null ?
                fournisseurDto.getCommandeFournisseurs().stream()
                        .map(CommandeFournisseurDto::toEntity)
                        .collect(Collectors.toList()) : null);

        return fournisseur;
    }
}
