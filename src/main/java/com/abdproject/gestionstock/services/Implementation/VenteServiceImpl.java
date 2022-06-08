package com.abdproject.gestionstock.services.Implementation;


import com.abdproject.gestionstock.dto.ArticleDto;
import com.abdproject.gestionstock.dto.LigneVenteDto;
import com.abdproject.gestionstock.dto.MouvementStockDto;
import com.abdproject.gestionstock.dto.VenteDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;

import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.*;
import com.abdproject.gestionstock.repository.ArticleRepository;
import com.abdproject.gestionstock.repository.LigneVenteRepository;
import com.abdproject.gestionstock.repository.VenteRepository;
import com.abdproject.gestionstock.services.MouvementStockService;
import com.abdproject.gestionstock.services.VenteService;
import com.abdproject.gestionstock.validator.VenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VenteServiceImpl implements VenteService {


    private VenteRepository venteRepository;
    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MouvementStockService mouvementStockService;

    @Autowired
    public VenteServiceImpl(VenteRepository venteRepository, LigneVenteRepository ligneVenteRepository, ArticleRepository articleRepository, MouvementStockService mouvementStockService){
        this.venteRepository = venteRepository;
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mouvementStockService = mouvementStockService;
    }

    @Override
    public VenteDto save(VenteDto dto) {
        List<String> errors = VenteValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("vente n'est pas valide {}", dto);
            throw new InvalidEntityException("vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        List<String> articleErrors = new ArrayList<>();
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()){
                articleErrors.add("Aucun article avec l'ID" + ligneVenteDto.getArticle().getId() + "n'a été trouvé dans la BDD");
            }
        });

        if (!articleErrors.isEmpty()){
            log.error("One or more articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas été trouvé  dans la BDD", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        Vente savedVente = venteRepository.save(VenteDto.toEntity(dto));
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVente);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });

        return VenteDto.fromEntity(savedVente);
    }

    @Override
    public VenteDto findById(Integer id) {
        if (id == null) {
            log.warn("Commande Client ID is null");
            return null;
        }

        return venteRepository.findById(id)
                .map(VenteDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente client n'a été trouvé avec l'ID" + id, ErrorCodes.VENTE_NOT_FOUND
                ));
    }

    @Override
    public VenteDto findByCode(String code) {
        if (StringUtils.hasLength(code)) {
            log.error("Vente CODE is null");
            return null;
        }
        return venteRepository.findVenteByCode(code)
                .map(VenteDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente n'a été trouvé avec le CODE" + code, ErrorCodes.VENTE_NOT_VALID
                ));
    }

    @Override
    public List<VenteDto> findAll() {
        return venteRepository.findAll().stream()
                .map(VenteDto::fromEntity)
                .collect(Collectors.toList());    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Vente id is null");
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une vente deja utilisé par des lignes ventes", ErrorCodes.VENTE_IN_USE);
        }
        venteRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente lig){
        MouvementStockDto mouvementStockDto = MouvementStockDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.VENTE)
                .quantite(lig.getQuantite())
                .identreprise(lig.getIdentreprise())
                .build();
        mouvementStockService.sortieStock(mouvementStockDto);
    }
}
