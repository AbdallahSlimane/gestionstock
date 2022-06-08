package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.LigneVente;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;


@Data
@Builder
public class LigneVenteDto {

    private Integer id;

    private VenteDto vente;

    private BigDecimal quantite;

    private Integer identreprise;

    private ArticleDto article;

    private BigDecimal prixUnitaire;

    public static LigneVenteDto fromEntity(LigneVente ligneVente){
        if (ligneVente == null){
            return null;
            // TODO throw exception
        }
        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .quantite(ligneVente.getQuantite())
                .identreprise(ligneVente.getIdentreprise())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .vente(VenteDto.fromEntity(ligneVente.getVente()))
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto){
        if (ligneVenteDto == null){
            return null;
            // TODO throw exception
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setIdentreprise(ligneVenteDto.getIdentreprise());
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setVente((VenteDto.toEntity(ligneVenteDto.getVente())));

        return ligneVente;
    }
}
