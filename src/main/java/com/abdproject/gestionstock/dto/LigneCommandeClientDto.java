package com.abdproject.gestionstock.dto;


import com.abdproject.gestionstock.model.LigneCommandeClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeClientDto {

    private Integer id;

    private ArticleDto article;

    @JsonIgnore
    private CommandeClientDto commandeClient;

    private Integer identreprise;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){
        if (ligneCommandeClient == null){
            return null;
            // TODO throw exception
        }

        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .quantite(ligneCommandeClient.getQuantite())
                .identreprise(ligneCommandeClient.getIdentreprise())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .commandeClient(CommandeClientDto.fromEntity(ligneCommandeClient.getCommandeClient()))
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto){
        if (ligneCommandeClientDto == null){
            return null;
            // TODO throw exception
        }

        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setIdentreprise(ligneCommandeClientDto.getIdentreprise());
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClient.setCommandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeClient()));
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));

        return ligneCommandeClient;
    }
}
