package com.abdproject.gestionstock.dto;



import com.abdproject.gestionstock.model.CommandeFournisseur;
import com.abdproject.gestionstock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeFournisseurDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private FournisseurDto fournisseur;

    private Integer identreprise;

    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur){
        if (commandeFournisseur == null){
            return null;
            // TODO throws exception
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .identreprise(commandeFournisseur.getIdentreprise())
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto){
        if (commandeFournisseurDto == null){
            return null;
            // TODO throws exception
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setIdentreprise(commandeFournisseurDto.getIdentreprise());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());

        return commandeFournisseur;

    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
