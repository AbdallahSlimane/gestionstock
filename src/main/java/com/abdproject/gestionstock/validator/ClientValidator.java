package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientDto clientDto){
        List<String> errors = new ArrayList<>();

        if (clientDto == null){
            errors.add("Veuillez renseigner le champ Nom");
            errors.add("Veuillez renseigner le champ Prénom");
            errors.add("Veuillez renseigner le champ Mail");
            errors.add("Veuillez renseigner le champ Numéro de téléphone ");

            return errors;

        }

        if (!StringUtils.hasLength(clientDto.getNom())){
            errors.add("Veuillez renseigner le champ Nom");
        }

        if (!StringUtils.hasLength(clientDto.getPrenom())){
            errors.add("Veuillez renseigner le champ Prénom");
        }

        if (!StringUtils.hasLength(clientDto.getMail())){
            errors.add("Veuillez renseigner le champ Mail");
        }

        if (!StringUtils.hasLength(clientDto.getNumTel())){
            errors.add("Veuillez renseigner le champ Numéro de téléphone ");
        }


        return errors;
    }
}
