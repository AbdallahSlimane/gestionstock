package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class ClientDto {

    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String photo;

    private String mail;

    private String numTel;

    private Integer identreprise;


    @JsonIgnore
    private List<CommandeClientDto> commandeClients;

    public static ClientDto fromEntity(Client client){
        if (client == null){
            return null;
            // TODO throws exception
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .identreprise(client.getIdentreprise())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .commandeClients(client.getCommandeClients() != null ?
                        client.getCommandeClients().stream()
                                .map(CommandeClientDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static Client toEntity(ClientDto clientDto){
        if (clientDto == null){
            return null;
            // TODO throws exception
        }

        Client client = new Client();
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setMail(clientDto.getMail());
        client.setPhoto(clientDto.getPhoto());
        client.setIdentreprise(clientDto.getIdentreprise());
        client.setNumTel(clientDto.getNumTel());
        client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
        client.setCommandeClients(clientDto.getCommandeClients() != null ?
                clientDto.getCommandeClients().stream()
                        .map(CommandeClientDto::toEntity)
                        .collect(Collectors.toList()) : null);

        return client;
    }
}
