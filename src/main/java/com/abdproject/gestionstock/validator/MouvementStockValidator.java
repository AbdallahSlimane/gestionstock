package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.MouvementStockDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MouvementStockValidator {

    public static List<String> validate(MouvementStockDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez sélectionner un Article");
            errors.add("Veuillez renseigner le champ Quantité");
            errors.add("Veuillez renseigner le champ Date du mouvement");
            errors.add("Veuillez renseigner le champ Type de mouvement");
            return errors;
        }

        if (dto.getArticle() == null || dto.getArticle().getId() == null){
            errors.add("Veuillez sélectionner un Article");
        }

        if (dto.getQuantite() == null || dto.getQuantite().compareTo(BigDecimal.ZERO) == 0){
            errors.add("Veuillez renseigner le champ Quantité");
        }

        if (dto.getDateMvt() == null){
            errors.add("Veuillez renseigner le champ Date du mouvement");
        }

        if (!StringUtils.hasLength(dto.getTypeMvt().name())){
            errors.add("Veuillez renseigner le champ Type de mouvement");
        }


        return errors;
    }
}
