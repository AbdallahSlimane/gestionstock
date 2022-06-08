package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilsateurValidator {

    public  static List<String> validate(UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();

        if (utilisateurDto == null){
            errors.add("veuillez renseigner le champ nom");
            errors.add("veuillez renseigner le champ mail");
            errors.add("veuillez renseigner le champ prénom");
            errors.add("veuillez renseigner le champ mot de passe");
            errors.add("veuillez renseigner le champ Adresse");

            return errors;

        }

        if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getNom()))
            errors.add("veuillez renseigner le champ nom");

        if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getMail()))
            errors.add("veuillez renseigner le champ mail");

        if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getPrenom()))
            errors.add("veuillez renseigner le champ prénom");

        if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getMotDePasse()))
            errors.add("veuillez renseigner le champ mot de passe");

        if (utilisateurDto.getDateDeNaissance() == null){
            errors.add("veuillez renseigner le champ date de naissance");
        }
        if (utilisateurDto.getAdresse() == null) {
            errors.add("veuillez renseigner le champ Adresse");
        } else {

            if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1()))
                errors.add("veuillez renseigner le champ adresse 1");

            if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getVille()))
                errors.add("veuillez renseigner le champ Ville");

            if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale()))
                errors.add("veuillez renseigner le champ Code postale");

            if (utilisateurDto == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getPays()))
                errors.add("veuillez renseigner le champ Pays");
        }

        return errors;
    }
}
