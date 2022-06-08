package com.abdproject.gestionstock.repository;

import com.abdproject.gestionstock.model.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Integer> {

    @Query("select sum(m.quantite) from MouvementStock m where m.article.id = :idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);

    List<MouvementStock> findAllByArticleId(Integer idArticle);
}
