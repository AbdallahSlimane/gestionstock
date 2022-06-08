package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/categories")
public interface CategoryApi {


    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une catégorie", notes = "Cette methode permet d'enregister ou modifier une catégorie", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet categorie créer / modifier"),
            @ApiResponse(code=400, message = "La categorie n'est pas valide")

    })
    CategoryDto save(@RequestBody CategoryDto dto);

    @GetMapping(value = APP_ROOT + "categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une catégorie par id", notes = "Cette methode permet de chercher une catégorie par id", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet categorie a été trouvé avec cet id"),
            @ApiResponse(code=404, message = "La categorie n'a pas été trouvé avec cet id")

    })
    CategoryDto findById(@PathVariable("idCategory") Integer id);

    @GetMapping(value = APP_ROOT + "categories/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une catégorie par code", notes = "Cette methode permet de chercher une catégorie par code", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet categorie a été trouvé avec ce code"),
            @ApiResponse(code=404, message = "La categorie n'a pas été trouvé avec ce code")

    })
    CategoryDto findByCode(@PathVariable("code") String code);

    @GetMapping(value = APP_ROOT + "categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des catégorie", notes = "Cette methode permet de chercher et renvoyer la liste des catégorie", responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des categories / une liste vide"),

    })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "categories/delete/{idCategory}")
    @ApiOperation(value = "supprimer une catégorie par id", notes = "Cette methode permet de supprimer une catégorie par id", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet categorie a été supprimé"),

    })
    void delete(@PathVariable("idCategory") Integer id);
}
