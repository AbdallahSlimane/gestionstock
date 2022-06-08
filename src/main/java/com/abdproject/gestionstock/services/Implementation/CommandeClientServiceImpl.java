package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.*;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;
import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.*;
import com.abdproject.gestionstock.repository.ArticleRepository;
import com.abdproject.gestionstock.repository.ClientRepository;
import com.abdproject.gestionstock.repository.CommandeClientRepository;
import com.abdproject.gestionstock.repository.LigneCommandeClientRepository;
import com.abdproject.gestionstock.services.CommandeClientService;
import com.abdproject.gestionstock.services.MouvementStockService;
import com.abdproject.gestionstock.validator.ArticleValidator;
import com.abdproject.gestionstock.validator.CommandeClientValidator;
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
public class CommandeClientServiceImpl implements CommandeClientService {


    private CommandeClientRepository commandeClientRepository;
    private ClientRepository clientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ArticleRepository articleRepository;
    private MouvementStockService mouvementStockService;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, ClientRepository clientRepository, ArticleRepository articleRepository, LigneCommandeClientRepository ligneCommandeClientRepository, MouvementStockService mouvementStockService) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.mouvementStockService = mouvementStockService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        List<String> errors = CommandeClientValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("la commande client n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande après livraison"
                    , ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (client.isEmpty()){
            log.warn("Client with ID {} was not found in the DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec L'ID" + dto.getClient().getId() + "n'a été trouvé dans la BDD"
                    , ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeClients() != null){
            dto.getLigneCommandeClients().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                    if (article.isEmpty()){
                        articleErrors.add("l'article avec l'ID" + ligCmdClt.getArticle().getId() + "n'existe pas");
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

        CommandeClient saveCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        if (dto.getLigneCommandeClients() != null){
            dto.getLigneCommandeClients().forEach(ligCmdClt -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClient(saveCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);
            });
        }


        return CommandeClientDto.fromEntity(saveCmdClt);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande Client ID is null");
            return null;
        }

        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a été trouvé avec l'ID" + id, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));

    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (code == null) {
            log.error("Commande Client CODE is null");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a été trouvé avec l'CODE" + code, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande Client ID is null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un commande client deja utilisé par des lignes commandes client", ErrorCodes.COMMANDE_CLIENT_IN_USE);
        }
        commandeClientRepository.deleteById(id);
    }


    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'etat de la commande client ID is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ETAT null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        commandeClientDto.setEtatCommande(etatCommande);

        CommandeClient savedCommandeClient = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));
        if (commandeClientDto.isCommandeLivree()){
            updateMvtStk(idCommande);
        }
        return CommandeClientDto.fromEntity(savedCommandeClient);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("l'ID de la Ligne commande is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou egal à zero",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient>  ligneCommandeClientOptional= findLigneCommandeClient(idLigneCommande);

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        return commandeClientDto;

    }


    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient == null){
            log.error("l'ID du client is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun client n'a été trouvé avec l'ID" + idClient, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
            );
        }
        commandeClientDto.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto))
        );
    }


    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun article n'a été trouvé avec l'ID" + idArticle, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
            );
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw  new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeClient ligneCommandeClieentToSaved = ligneCommandeClient.get();

        ligneCommandeClieentToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClieentToSaved);

        return commandeClientDto;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);

        // Informer l'utilisateur ou le client si la ligne n'existe pas
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);


        return commandeClientDto;
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande client ID is null");
            throw  new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null){
            log.error("l'ID de la Ligne commande is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null){
            log.error("l'ID de " + msg + "l'article is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID Article null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande) {
        CommandeClientDto commandeClientDto = findById(idCommande);
        if (commandeClientDto.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande après livraison",
                    ErrorCodes.COMMANDE_CLIENT_NOT_MODIFIABLE);
        }
        return commandeClientDto;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneCommandeClient>  ligneCommandeClientOptional= ligneCommandeClientRepository.findById(idLigneCommande);
        if (ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune ligne commande client n'a été trouvé avec l'ID" + idLigneCommande, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
            );
        }
        return ligneCommandeClientOptional;
    }

    private void updateMvtStk(Integer idCommande){
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig -> {
            MouvementStockDto mouvementStockDto = MouvementStockDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.SORTIE)
                    .sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
                    .quantite(lig.getQuantite())
                    .identreprise(lig.getIdentreprise())
                    .build();
            mouvementStockService.sortieStock(mouvementStockDto);
        });
    }
}
