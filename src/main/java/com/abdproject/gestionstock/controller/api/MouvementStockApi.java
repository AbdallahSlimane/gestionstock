package com.abdproject.gestionstock.controller.api;

import com.abdproject.gestionstock.dto.MouvementStockDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/mouvementstocks")
public interface MouvementStockApi {

    @GetMapping(value = APP_ROOT + "/mouvementstocks/stockreel/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);


    @GetMapping(value = APP_ROOT + "/mouvementstocks/filter/article/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(value = APP_ROOT + "/mouvementstocks/entree", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    MouvementStockDto entreeStock(@RequestBody MouvementStockDto dto);

    @PostMapping(value = APP_ROOT + "/mouvementstocks/sortie", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    MouvementStockDto sortieStock(@RequestBody MouvementStockDto dto);

    @PostMapping(value = APP_ROOT + "/mouvementstocks/correctionstockpos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    MouvementStockDto correctionStockPos(@RequestBody MouvementStockDto dto);

    @PostMapping(value = APP_ROOT + "/mouvementstocks/correctionstockneg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    MouvementStockDto correctionStockNeg(@RequestBody MouvementStockDto dto);

}
