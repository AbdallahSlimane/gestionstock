package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.CommandeClientDto;
import com.abdproject.gestionstock.dto.CommandeFournisseurDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeFournisseurDto;
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

@Api(APP_ROOT +"/commandefournisseurs")

public interface CommandeFournisseurApi {

    @PostMapping(value = APP_ROOT + "/commandefournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une commande fournisseur", notes = "Cette methode permet d'enregister ou modifier une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet commande fournisseur créer / modifier"),
            @ApiResponse(code=400, message = "La commande fournisseur n'est pas valide")

    })
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @GetMapping(value = APP_ROOT + "commandefournisseurs/{idCommandeFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande fournisseur", notes = "Cette methode permet de chercher une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet commande fournisseur a été trouvé"),
            @ApiResponse(code=400, message = "La commande fournisseur n'a pas été trouvé avec cet id")

    })
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(value = APP_ROOT + "commandefournisseurs/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande fournisseur par son code", notes = "Cette methode permet de chercher une commande fournisseur par son code", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La commande fournisseur a été trouvé avec ce code"),
            @ApiResponse(code=404, message = "La commande fournisseur n'a pas été trouvé avec ce code")

    })
    CommandeFournisseurDto findByCode(@PathVariable("code") String code);

    @PatchMapping(value = APP_ROOT + "commandefournisseurs/update/etat/{idCommande}/{etatCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = APP_ROOT + "commandefournisseurs/update/client/{idCommande}/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @PatchMapping(value = APP_ROOT + "commandefournisseurs/update/article/{idCommande}/{idArticle}/{idLigneCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idArticle") Integer idArticle, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @PatchMapping(value = APP_ROOT + "commandefournisseurs/update/quantite/{idCommande}/{idLigneCommande}/{quantite}", produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,@PathVariable("idLigneCommande") Integer idLigneCommande,@PathVariable("quantite") BigDecimal quantite);

    @DeleteMapping(value = APP_ROOT + "commandefournisseurs/delete/article/{idLigneCommande}/{idCommande}")
    CommandeFournisseurDto deleteArticle(@PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idCommande") Integer idCommande);

    @GetMapping(value = APP_ROOT + "commandefournisseurs/lignesCommande/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

    @GetMapping(value = APP_ROOT + "commandefournisseurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des commandes fournisseurs", notes = "Cette methode permet de chercher et renvoyer la liste des commandes fournisseurs", responseContainer = "List<CommandeFournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "la liste des commandes fournisseur / une liste vide"),
    })
    List<CommandeFournisseurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "commandefournisseurs/delete/{idCommandeFournisseur}")
    @ApiOperation(value = "supprimer une commande fournisseur", notes = "Cette methode permet de supprimer une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet commande fournisseur a été supprimé"),
    })
    void delete(@PathVariable("idCommandeFournisseur") Integer id);

}
