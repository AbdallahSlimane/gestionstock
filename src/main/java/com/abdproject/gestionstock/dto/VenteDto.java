package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.Vente;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class VenteDto {

    private Integer id;

    private String code;

    private Instant dateVente;

    private Integer identreprise;

    private  String commentaire;

    private List<LigneVenteDto> ligneVentes;

    public static VenteDto fromEntity(Vente vente){
        if (vente == null){
            return null;
            // TODO throw exception
        }

        return VenteDto.builder()
                .id(vente.getId())
                .code(vente.getCode())
                .identreprise(vente.getIdentreprise())
                .dateVente(vente.getDateVente())
                .commentaire(vente.getCommentaire())
                .build();
    }

    public static Vente toEntity(VenteDto venteDto){
        if (venteDto == null){
            return null;
            // TODO throw exception
        }

        Vente vente = new Vente();
        vente.setId(venteDto.getId());
        vente.setIdentreprise(venteDto.getIdentreprise());
        vente.setCode(venteDto.getCode());
        vente.setDateVente(venteDto.getDateVente());
        vente.setCommentaire(venteDto.getCommentaire());

        return vente;
    }
}
