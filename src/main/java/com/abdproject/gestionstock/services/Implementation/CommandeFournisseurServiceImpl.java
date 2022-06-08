package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.*;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;
import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.*;
import com.abdproject.gestionstock.repository.ArticleRepository;
import com.abdproject.gestionstock.repository.CommandeFournisseurRepository;
import com.abdproject.gestionstock.repository.FournisseurRepository;
import com.abdproject.gestionstock.repository.LigneCommandeFournisseurRepository;
import com.abdproject.gestionstock.services.CommandeFournisseurService;
import com.abdproject.gestionstock.services.MouvementStockService;
import com.abdproject.gestionstock.validator.ArticleValidator;
import com.abdproject.gestionstock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private ArticleRepository articleRepository;
    private MouvementStockService mouvementStockService;

    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository, ArticleRepository articleRepository, FournisseurRepository fournisseurRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, MouvementStockService mouvementStockService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.articleRepository = articleRepository;
        this.mouvementStockService = mouvementStockService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("la commande fournisseur n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande après livraison"
                    , ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (fournisseur.isEmpty()){
            log.warn("Fournisseur with ID {} was not found in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec L'ID" + dto.getFournisseur().getId() + "n'a été trouvé dans la BDD"
                    , ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrn -> {
                if (ligCmdFrn.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdFrn.getArticle().getId());
                    if (article.isEmpty()){
                        articleErrors.add("l'article avec l'ID" + ligCmdFrn.getArticle().getId() + "n'existe pas");
                    } else {
                        articleErrors.add("Impossible d'enregistrer une commande avec un article NULL");
                    }
                }
            });
        }

        if (!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("Cet article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeFournisseur saveCmdFrn = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

        if (dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrn -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrn);
                ligneCommandeFournisseur.setCommandeFournisseur(saveCmdFrn);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }


        return CommandeFournisseurDto.fromEntity(saveCmdFrn);    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Commande Fournisseur ID is null");
            return null;
        }

        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a été trouvé avec l'ID" + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (code == null) {
            log.error("Commande Fournisseur CODE is null");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a été trouvé avec l'CODE" + code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));     }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande Fournisseur ID is null");
            return;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisé par des lignes commandes fournisseur", ErrorCodes.COMMANDE_FOURNISSEUR_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'etat de la commande fournisseur ID is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ETAT null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);
        commandeFournisseurDto.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));
        if (commandeFournisseurDto.isCommandeLivree()){
            updateMvtStk(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("l'ID de la Ligne commande is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou egal à zero",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur>  ligneCommandeFournisseurOptional= findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseurDto;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null){
            log.error("l'ID du fournisseur is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun fournisseur n'a été trouvé avec l'ID" + idFournisseur, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE
            );
        }
        commandeFournisseurDto.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto))
        );
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun article n'a été trouvé avec l'ID" + idArticle, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw  new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();

        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseurDto;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        // Informer l'utilisateur ou le client si la ligne n'existe pas
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);


        return commandeFournisseurDto;    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande fournisseur ID is null");
            throw  new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null){
            log.error("l'ID de la Ligne commande is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null){
            log.error("l'ID de " + msg + "l'article is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID Article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }
    }

    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        if (commandeFournisseurDto.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande après livraison",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_MODIFIABLE);
        }
        return commandeFournisseurDto;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur>  ligneCommandeFournisseurOptional= ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a été trouvé avec l'ID" + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
            );
        }
        return ligneCommandeFournisseurOptional;
    }

    private void updateMvtStk(Integer idCommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseurs.forEach(lig -> {
            MouvementStockDto mouvementStockDto = MouvementStockDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.ENTREE)
                    .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .identreprise(lig.getIdentreprise())
                    .build();
            mouvementStockService.entreeStock(mouvementStockDto);
        });
    }
}
