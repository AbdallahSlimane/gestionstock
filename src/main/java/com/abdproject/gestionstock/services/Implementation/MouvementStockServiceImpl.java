package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.MouvementStockDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;
import com.abdproject.gestionstock.model.Client;
import com.abdproject.gestionstock.model.MouvementStock;
import com.abdproject.gestionstock.model.TypeMvtStk;
import com.abdproject.gestionstock.repository.MouvementStockRepository;
import com.abdproject.gestionstock.services.ArticleService;
import com.abdproject.gestionstock.services.MouvementStockService;
import com.abdproject.gestionstock.validator.MouvementStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MouvementStockServiceImpl implements MouvementStockService {

    private MouvementStockRepository mouvementStockRepository;
    private ArticleService articleService;

    @Autowired
    public MouvementStockServiceImpl(MouvementStockRepository mouvementStockRepository, ArticleService articleService) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.articleService = articleService;
    }


    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if (idArticle == null){
            log.warn("ID article is NULL");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mouvementStockRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MouvementStockDto> mvtStkArticle(Integer idArticle) {
        return mouvementStockRepository.findAllByArticleId(idArticle).stream()
                .map(MouvementStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MouvementStockDto entreeStock(MouvementStockDto dto) {
        return entreePositive(dto, TypeMvtStk.ENTREE);
    }

    @Override
    public MouvementStockDto sortieStock(MouvementStockDto dto) {
        return sortieNegative(dto, TypeMvtStk.SORTIE);
    }

    @Override
    public MouvementStockDto correctionStockPos(MouvementStockDto dto) {
        return entreePositive(dto, TypeMvtStk.CORRECTION_POS);
    }

    @Override
    public MouvementStockDto correctionStockNeg(MouvementStockDto dto) {
        return sortieNegative(dto, TypeMvtStk.CORRECTION_NEG);
    }

    private MouvementStockDto entreePositive(MouvementStockDto dto, TypeMvtStk typeMvtStk){
        List<String> errors = MouvementStockValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("le mouvement de stock n'est pas valide {}", dto);
            throw new InvalidEntityException("le mouvement de stock n'est pas valide", ErrorCodes.MOUVEMENT_STOCK_NOT_VALID, errors);
        }

        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(TypeMvtStk.ENTREE);
        return MouvementStockDto.fromEntity(
                mouvementStockRepository.save(MouvementStockDto.toEntity(dto))
        );
    }

    private MouvementStockDto sortieNegative(MouvementStockDto dto, TypeMvtStk typeMvtStk){
        List<String> errors = MouvementStockValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("le mouvement de stock n'est pas valide {}", dto);
            throw new InvalidEntityException("le mouvement de stock n'est pas valide", ErrorCodes.MOUVEMENT_STOCK_NOT_VALID, errors);
        }

        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvt(TypeMvtStk.SORTIE);
        return MouvementStockDto.fromEntity(
                mouvementStockRepository.save(MouvementStockDto.toEntity(dto))
        );
    }
}
