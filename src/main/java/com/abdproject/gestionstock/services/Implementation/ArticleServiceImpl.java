package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.ArticleDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeFournisseurDto;
import com.abdproject.gestionstock.dto.LigneVenteDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;
import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.Article;
import com.abdproject.gestionstock.model.LigneCommandeClient;
import com.abdproject.gestionstock.model.LigneCommandeFournisseur;
import com.abdproject.gestionstock.model.LigneVente;
import com.abdproject.gestionstock.repository.*;
import com.abdproject.gestionstock.services.ArticleService;
import com.abdproject.gestionstock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository ligneVenteRepository, LigneCommandeClientRepository ligneCommandeClientRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository){
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("l'article n'est pas valide {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto)
                )
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if (id == null){
            log.error("Article id is null");
            return null;
        }

        Optional<Article> article = articleRepository.findById(id);


        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (StringUtils.hasLength(codeArticle)){
            log.error("Article CODE is null");
            return null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);


        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec ce CODE = " + codeArticle + "n'a été trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND));    }

    @Override
    public List<ArticleDto> findAll() {

        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Article id is null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article deja utilisé dans des commandes client", ErrorCodes.ARTICLE_IN_USE);
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article deja utilisé dans des commandes fournisseur", ErrorCodes.ARTICLE_IN_USE);
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article deja utilisé dans des ventes", ErrorCodes.ARTICLE_IN_USE);
        }
        articleRepository.deleteById(id);
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return ligneVenteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findCommandeFournisseur(Integer idArticle) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }
}
