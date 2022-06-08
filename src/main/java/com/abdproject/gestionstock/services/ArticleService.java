package com.abdproject.gestionstock.services;

import com.abdproject.gestionstock.dto.ArticleDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeFournisseurDto;
import com.abdproject.gestionstock.dto.LigneVenteDto;
import com.abdproject.gestionstock.model.LigneVente;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneCommandeFournisseurDto> findCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);

    void delete(Integer id);
}
