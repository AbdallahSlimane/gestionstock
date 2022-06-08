package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.ArticleDto;
import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeFournisseurDto;
import com.abdproject.gestionstock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/articles")
public interface ArticleApi {


    @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un article", notes = "Cette methode permet d'enregister ou modifier un article", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'objet article créer / modifier"),
            @ApiResponse(code=400, message = "L'article n'est pas valide")

    })
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(value = APP_ROOT + "articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un article par id", notes = "Cette methode permet de chercher par son id", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'article a été trouvé"),
            @ApiResponse(code=404, message = "L'article n'a pas été trouvé avec cet l'id")

    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value = APP_ROOT + "articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un article code", notes = "Cette methode permet de chercher un article par son code", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'article a été trouvé avec ce code"),
            @ApiResponse(code=404, message = "L'article n'a pas été trouvé avec ce code")

    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value = APP_ROOT + "articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "La liste des articles / une liste vide"),

    })
    List<ArticleDto> findAll();

    @GetMapping(value = APP_ROOT + "articles/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT + "articles/historique/commandeclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT + "articles/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeFournisseurDto> findCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT + "articles/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idCategory);


    @DeleteMapping(value = APP_ROOT + "articles/delete/{idArticle}")
    @ApiOperation(value = "supprimer un article par id", notes = "Cette methode permet de supprimer un article par id", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "L'article a été supprimé"),

    })
    void delete(@PathVariable("idArticle") Integer id);
}
