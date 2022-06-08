package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.ClientDto;
import com.abdproject.gestionstock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/entreprises")

public interface EntrepriseApi {

    @PostMapping(value = APP_ROOT + "/entreprises/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une entreprise", notes = "Cette methode permet d'enregister ou modifier une entreprise", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet entreprise créer / modifier"),
            @ApiResponse(code=400, message = "L'entreprise n'est pas valide")

    })
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(value = APP_ROOT + "entreprises/{idEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une entreprise", notes = "Cette methode permet de chercher une entreprise", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet entreprise a été trouvé"),
            @ApiResponse(code=400, message = "L'entreprise n'a pas été trouvé avec cet id")

    })
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);


    @GetMapping(value = APP_ROOT + "entreprises/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des entreprises", notes = "Cette methode permet de chercher et renvoyer la liste des entreprises", responseContainer = "List<EntrepriseDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des entreprises / une liste vide"),

    })
    List<EntrepriseDto> findAll();

    @DeleteMapping(value = APP_ROOT + "entreprises/delete/{idEntreprise}")
    @ApiOperation(value = "supprimer une entreprise", notes = "Cette methode permet de supprimer une entreprise", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet entreprise a été supprimé"),
    })
    void delete(@PathVariable("idEntreprise") Integer id);
}
