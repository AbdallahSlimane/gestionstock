package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.ClientDto;
import com.abdproject.gestionstock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/utilisateurs")

public interface UtilisateurApi {

    @PostMapping(value = APP_ROOT + "/utilisateurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un utilisateur", notes = "Cette methode permet d'enregister ou modifier un utilisateur", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet utilisateur créer / modifier"),
            @ApiResponse(code=400, message = "L'utilisateur n'est pas valide")

    })
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(value = APP_ROOT + "utilisateurs/{idUtilisateur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un utilisateur", notes = "Cette methode permet de chercher un utilisateur par id", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet utilisateur a été trouvé"),
            @ApiResponse(code=400, message = "L'utilisateur n'a pas été trouvé")

    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(value = APP_ROOT + "utilisateurs/{mail}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un utilisateur par mail", notes = "Cette methode permet de chercher un utilisateur par mail", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet utilisateur a été trouvé"),
            @ApiResponse(code=400, message = "L'utilisateur n'a pas été trouvé")

    })
    UtilisateurDto findByMail(@PathVariable("mail") String mail);


    @GetMapping(value = APP_ROOT + "utilisateurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des utilisateurs", notes = "Cette methode permet de chercher et renvoyer la liste des utilisateurs", responseContainer = "List<UtilisateurDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des utilisateur / une liste vide"),
    })
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "utilisateurs/delete/{idUtilisateur}")
    @ApiOperation(value = "supprimer un utilisateur", notes = "Cette methode permet de supprimer un utilisateur par id", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet utilisateur a été supprimé"),
    })
    void delete(@PathVariable("idUtilisateur") Integer id);
}
