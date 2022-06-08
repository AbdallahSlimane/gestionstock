package com.abdproject.gestionstock.controller;

import com.abdproject.gestionstock.controller.api.MouvementStockApi;
import com.abdproject.gestionstock.dto.MouvementStockDto;
import com.abdproject.gestionstock.services.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MouvementStockController implements MouvementStockApi {

    private MouvementStockService mouvementStockService;

    @Autowired
    public MouvementStockController(MouvementStockService mouvementStockService) {
        this.mouvementStockService = mouvementStockService;
    }


    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return mouvementStockService.stockReelArticle(idArticle);
    }

    @Override
    public List<MouvementStockDto> mvtStkArticle(Integer idArticle) {
        return mouvementStockService.mvtStkArticle(idArticle);
    }

    @Override
    public MouvementStockDto entreeStock(MouvementStockDto dto) {
        return mouvementStockService.entreeStock(dto);
    }

    @Override
    public MouvementStockDto sortieStock(MouvementStockDto dto) {
        return mouvementStockService.sortieStock(dto);
    }

    @Override
    public MouvementStockDto correctionStockPos(MouvementStockDto dto) {
        return mouvementStockService.correctionStockPos(dto);
    }

    @Override
    public MouvementStockDto correctionStockNeg(MouvementStockDto dto) {
        return mouvementStockService.correctionStockNeg(dto);
    }

}
