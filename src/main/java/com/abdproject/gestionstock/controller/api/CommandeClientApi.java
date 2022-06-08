package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.CommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.model.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/commandeclients")

public interface CommandeClientApi {

    @PostMapping(value = APP_ROOT + "/commandeclients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une commande client", notes = "Cette methode permet d'enregister ou modifier une commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet commande client créer / modifier"),
            @ApiResponse(code=400, message = "La commande client n'est pas valide")

    })
    CommandeClientDto save(@RequestBody CommandeClientDto dto);

    @GetMapping(value = APP_ROOT + "commandeclients/{idCommandeClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande client par id", notes = "Cette methode permet de rechercher une commande client par id", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La commande client a été trouvé"),
            @ApiResponse(code=400, message = "La commande client n'a pas été trouvé avec cet id")

    })
    CommandeClientDto findById(@PathVariable("idCommandeClient") Integer id);

    @PatchMapping(value = APP_ROOT + "commandeclients/update/etat/{idCommande}/{etatCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeClientDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = APP_ROOT + "commandeclients/update/client/{idCommande}/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeClientDto updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping(value = APP_ROOT + "commandeclients/update/article/{idCommande}/{idArticle}/{idLigneCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeClientDto updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idArticle") Integer idArticle, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @PatchMapping(value = APP_ROOT + "commandeclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeClientDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,@PathVariable("idLigneCommande") Integer idLigneCommande,@PathVariable("quantite") BigDecimal quantite);

    @DeleteMapping(value = APP_ROOT + "commandeclients/delete/article/{idLigneCommande}/{idCommande}")
    CommandeClientDto deleteArticle(@PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idCommande") Integer idCommande);

    @GetMapping(value = APP_ROOT + "commandeclients/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande client par son code", notes = "Cette methode permet de chercher une commande client par son code", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La commande client a été trouvé avec ce code"),
            @ApiResponse(code=404, message = "La commande client n'a pas été trouvé avec ce code")

    })
    CommandeClientDto findByCode(@PathVariable("code") String code);

    @GetMapping(value = APP_ROOT + "commandeclients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des commandes clients", notes = "Cette methode permet de chercher et renvoyer la liste des commandes clients", responseContainer = "List<CommandeClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "la liste des commandes clients / une liste vide"),
    })
    List<CommandeClientDto> findAll();


    @GetMapping(value = APP_ROOT + "commandeclients/lignesCommande/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(value = APP_ROOT + "commandeclients/delete/{idCommandeClient}")
    @ApiOperation(value = "supprimer une commande client par id", notes = "Cette methode permet de supprimer une commande client par id", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La commande client a été supprimer"),

    })
    void delete(@PathVariable("idCommandeClient") Integer id);
}
