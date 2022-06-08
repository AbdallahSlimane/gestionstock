package com.abdproject.gestionstock.dto;


import com.abdproject.gestionstock.model.CommandeClient;
import com.abdproject.gestionstock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private ClientDto client;

    private Integer idEntreprise;

    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCommandeClients;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient){
        if (commandeClient == null){
            return null;
            // TODO throws exception
        }

        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .idEntreprise(commandeClient.getIdEntreprise())
                .etatCommande(commandeClient.getEtatCommande())
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){
        if (commandeClientDto == null){
            return null;
            // TODO throws exception
        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());

        return commandeClient;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
