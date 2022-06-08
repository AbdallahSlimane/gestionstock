package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.ArticleDto;
import com.abdproject.gestionstock.dto.VenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/ventes")

public interface VenteApi {

    @PostMapping(value = APP_ROOT + "/ventes/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une vente", notes = "Cette methode permet d'enregister ou modifier une vente", response = VenteDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet vente créer / modifier"),
            @ApiResponse(code=400, message = "La vente n'est pas valide")

    })
    VenteDto save(@RequestBody VenteDto dto);

    @GetMapping(value = APP_ROOT + "ventes/{idVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une vente", notes = "Cette methode permet de chercher une vente", response = VenteDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet vente a été trouvé"),
            @ApiResponse(code=400, message = "La vente n'a pas été trouvé")

    })
    VenteDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(value = APP_ROOT + "ventes/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une vente code", notes = "Cette methode permet de chercher une vente par son code", response = VenteDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La vente a été trouvé avec ce code"),
            @ApiResponse(code=404, message = "La vente n'a pas été trouvé avec ce code")

    })
    VenteDto findByCode(@PathVariable("code") String code);

    @GetMapping(value = APP_ROOT + "ventes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des vente", notes = "Cette methode permet de chercher et renvoyer la liste des ventes", responseContainer = "List<VenteDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des ventes / une liste vide"),

    })
    List<VenteDto> findAll();

    @DeleteMapping(value = APP_ROOT + "ventes/delete/{idVente}")
    @ApiOperation(value = "supprimer un vente par id", notes = "Cette methode permet de supprimer un vente par id", response = VenteDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La vente a été supprimé"),

    })
    void delete(@PathVariable("idVente") Integer id);
}
