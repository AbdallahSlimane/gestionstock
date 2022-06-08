package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.ClientDto;
import com.abdproject.gestionstock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le champ Nom");
            errors.add("Veuillez renseigner le champ Prénom");
            errors.add("Veuillez renseigner le champ Mail");
            errors.add("Veuillez renseigner le champ Numéro de téléphone ");

            return errors;

        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez renseigner le champ Nom");
        }

        if (!StringUtils.hasLength(dto.getPrenom())){
            errors.add("Veuillez renseigner le champ Prénom");
        }

        if (!StringUtils.hasLength(dto.getMail())){
            errors.add("Veuillez renseigner le champ Mail");
        }

        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("Veuillez renseigner le champ Numéro de téléphone ");
        }


        return errors;
    }
}
