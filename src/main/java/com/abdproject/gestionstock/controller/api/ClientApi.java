package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.ArticleDto;
import com.abdproject.gestionstock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/clients")

public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un client", notes = "Cette methode permet d'enregister ou modifier un client", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet client créer / modifier"),
            @ApiResponse(code=400, message = "Le client n'est pas valide")

    })
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(value = APP_ROOT + "clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un client", notes = "Cette methode permet de chercher un client par id", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet client a été trouvé"),
            @ApiResponse(code=400, message = "Le client n'a pas été trouvé")

    })
    ClientDto findById(@PathVariable("idClient") Integer id);


    @GetMapping(value = APP_ROOT + "clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des clients", notes = "Cette methode permet de chercher et renvoyer la liste des clients", responseContainer = "List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des clients / une liste vide"),

    })
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "clients/delete/{idClient}")
    @ApiOperation(value = "supprimer un client par id", notes = "Cette methode permet de supprimer un client par id", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Le client a été supprimer"),

    })
    void delete(@PathVariable("idClient") Integer id);
}
