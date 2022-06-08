package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le champ code");
            errors.add("Veuillez renseigner le champ Date de la commande");
            errors.add("Veuillez renseigner l'état de la commande");
            errors.add("Veuillez renseigner le champ client");

            return errors;
        }
        if (!StringUtils.hasLength(dto.getCode())){
            errors.add("Veuillez renseigner le champ code");
        }
        if (dto.getDateCommande() == null){
            errors.add("Veuillez renseigner le champ Date de la commande");
        }
        if(!StringUtils.hasLength(dto.getEtatCommande().toString())){
            errors.add("Veuillez renseigner l'état de la commande");
        }
        if (dto.getClient() == null || dto.getClient().getId() == null){
            errors.add("Veuillez renseigner le champ client");
        }

        return errors;
    }
}
