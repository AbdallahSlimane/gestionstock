package com.abdproject.gestionstock.controller;

import com.abdproject.gestionstock.controller.api.CommandeClientApi;
import com.abdproject.gestionstock.dto.CommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.model.EtatCommande;
import com.abdproject.gestionstock.services.CommandeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeClientController implements CommandeClientApi {

    private CommandeClientService commandeClientService;

    @Autowired
    public CommandeClientController(CommandeClientService commandeClientService) {
        this.commandeClientService = commandeClientService;
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return commandeClientService.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeClientService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        return commandeClientService.updateClient(idCommande, idClient);
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idArticle, Integer idLigneCommande) {
        return commandeClientService.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idLigneCommande, Integer idCommande) {
        return commandeClientService.deleteArticle(idLigneCommande, idCommande);
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return commandeClientService.findAllLignesCommandesClientByCommandeClientId(idCommande);
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        return commandeClientService.save(dto);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        return commandeClientService.findById(id);
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        return commandeClientService.findByCode(code);
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        commandeClientService.delete(id);
    }
}
