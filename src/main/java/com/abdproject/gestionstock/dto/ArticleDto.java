package com.abdproject.gestionstock.dto;


import com.abdproject.gestionstock.model.Article;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ArticleDto {

    private Integer id;

    private String codeArticle;

    private String despcription;

    private BigDecimal prixUnitaireHt;

    private BigDecimal tauxTva;

    private BigDecimal prixUnitaireTTC;

    private String photo;

    private Integer identreprise;

    private CategoryDto category;

    public static ArticleDto fromEntity(Article article){
        if(article == null){
            return null;
            // TODO throws exception
        }

        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .despcription(article.getDespcription())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTTC((article.getPrixUnitaireTTC()))
                .tauxTva(article.getTauxTva())
                .photo(article.getPhoto())
                .identreprise(article.getIdentreprise())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .build();
    }

    public static Article toEntity(ArticleDto articleDto){
        if (articleDto == null){
            return null;
            // TODO throws exception
        }

        Article article = new Article();
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDespcription(articleDto.getDespcription());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTTC(articleDto.getPrixUnitaireTTC());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
        article.setTauxTva(articleDto.getTauxTva());
        article.setIdentreprise(articleDto.getIdentreprise());
        article.setPhoto(articleDto.getPhoto());

        return article;
    }
}
