package com.abdproject.gestionstock.services;

import com.abdproject.gestionstock.dto.CommandeClientDto;
import com.abdproject.gestionstock.dto.CommandeFournisseurDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeFournisseurDto;
import com.abdproject.gestionstock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    //Delete article ===> LigneCommandeClient
    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    void delete(Integer id);
}
