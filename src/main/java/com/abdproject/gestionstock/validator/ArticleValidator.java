package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto dto){
        List<String> errors = new ArrayList<>();


        if (dto == null ){
            errors.add("Veuillez renseigner le champ code de l'article");
            errors.add("Veuillez renseigner le champ description");
            errors.add("Veuillez renseigner le champ Prix unitaire HT");
            errors.add("Veuillez renseigner le champ TTC");
            errors.add("Veuillez renseigner le champ Taux TVA");
            errors.add("Veuillez Selectionner une Catégorie");
            return errors;

        }

        if (!StringUtils.hasLength(dto.getCodeArticle())){
            errors.add("Veuillez renseigner le champ code de l'article");
        }

        if (!StringUtils.hasLength(dto.getDespcription())){
            errors.add("Veuillez renseigner le champ description");
        }

        if (dto.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le champ Prix unitaire HT");
        }

        if (dto.getPrixUnitaireTTC() == null){
            errors.add("Veuillez renseigner le champ TTC");
        }

        if (dto.getTauxTva() == null){
            errors.add("Veuillez renseigner le champ Taux TVA");
        }

        if (dto.getCategory() == null){
            errors.add("Veuillez Selectionner une Catégorie");
        }
        return errors;
    }
}
