package com.abdproject.gestionstock.dto;

import com.abdproject.gestionstock.model.MouvementStock;
import com.abdproject.gestionstock.model.SourceMvtStk;
import com.abdproject.gestionstock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MouvementStockDto {

    private Integer id;

    private ArticleDto article;

    private BigDecimal quantite;

    private Instant dateMvt;

    private Integer identreprise;

    private SourceMvtStk sourceMvt;

    private TypeMvtStk typeMvt;

    public static MouvementStockDto fromEntity(MouvementStock mouvementStock){
        if (mouvementStock == null){
            return null;
            // TODO throw exception
        }

        return MouvementStockDto.builder()
                .id(mouvementStock.getId())
                .quantite(mouvementStock.getQuantite())
                .dateMvt(mouvementStock.getDateMvt())
                .typeMvt(mouvementStock.getTypeMvt())
                .identreprise(mouvementStock.getIdentreprise())
                .sourceMvt(mouvementStock.getSourceMvt())
                .article(ArticleDto.fromEntity(mouvementStock.getArticle()))
                .build();
    }

    public static MouvementStock toEntity(MouvementStockDto mouvementStockDto){
        if (mouvementStockDto == null){
            return null;
            // TODO throw exception
        }

        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setId(mouvementStockDto.getId());
        mouvementStock.setIdentreprise(mouvementStockDto.getIdentreprise());
        mouvementStock.setQuantite(mouvementStockDto.getQuantite());
        mouvementStock.setDateMvt(mouvementStockDto.getDateMvt());
        mouvementStock.setTypeMvt(mouvementStockDto.getTypeMvt());
        mouvementStock.setSourceMvt(mouvementStockDto.getSourceMvt());
        mouvementStock.setArticle(ArticleDto.toEntity(mouvementStockDto.getArticle()));

        return mouvementStock;

    }
}
