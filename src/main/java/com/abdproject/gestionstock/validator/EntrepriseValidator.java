package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le champ Nom");
            errors.add("Veuillez renseigner le champ Mail");
            errors.add("Veuillez renseigner le champ Numéro de téléphone ");
            errors.add("Veuillez renseigner le champ Code fiscale");
            errors.add("veuillez renseigner le champ Adresse");

            return errors;
        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez renseigner le champ Nom");
        }

        if (!StringUtils.hasLength(dto.getMail())){
            errors.add("Veuillez renseigner le champ Mail");
        }

        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("Veuillez renseigner le champ Numéro de téléphone ");
        }

        if (!StringUtils.hasLength(dto.getCodeFiscal())){
            errors.add("Veuillez renseigner le champ Code fiscale");
        }

        if (dto.getAdresse() == null) {
            errors.add("veuillez renseigner le champ Adresse");
        } else {

            if (dto == null || !StringUtils.hasLength(dto.getAdresse().getAdresse1()))
                errors.add("veuillez renseigner le champ adresse 1");

            if (dto == null || !StringUtils.hasLength(dto.getAdresse().getVille()))
                errors.add("veuillez renseigner le champ Ville");

            if (dto == null || !StringUtils.hasLength(dto.getAdresse().getCodePostale()))
                errors.add("veuillez renseigner le champ Code postale");

            if (dto == null || !StringUtils.hasLength(dto.getAdresse().getPays()))
                errors.add("veuillez renseigner le champ Pays");
        }

        return errors;
    }
}
