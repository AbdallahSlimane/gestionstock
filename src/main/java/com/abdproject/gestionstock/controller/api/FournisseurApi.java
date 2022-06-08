package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.ClientDto;
import com.abdproject.gestionstock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/fournisseurs")

public interface FournisseurApi {

    @PostMapping(value = APP_ROOT + "/fournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un fournisseur", notes = "Cette methode permet d'enregister ou modifier un fournisseur", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet fournisseur créer / modifier"),
            @ApiResponse(code=400, message = "Le fournisseur n'est pas valide")

    })
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(value = APP_ROOT + "fournisseurs/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un fournisseur", notes = "Cette methode permet de chercher un fournisseur par id", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet fournisseur a été trouvé"),
            @ApiResponse(code=400, message = "Le fournisseur n'a pas été trouvé")

    })
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);


    @GetMapping(value = APP_ROOT + "fournisseurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des fournisseurs", notes = "Cette methode permet de chercher et renvoyer la liste des fournisseurs ", responseContainer = "List<FournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des fournisseurs / une liste vide"),

    })
    List<FournisseurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "fournisseurs/delete/{idFournisseur}")
    @ApiOperation(value = "supprimer un fournisseur", notes = "Cette methode permet de chercher un supprimer par id", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet fournisseur a été supprimé"),

    })
    void delete(@PathVariable("idFournisseur") Integer id);
}
