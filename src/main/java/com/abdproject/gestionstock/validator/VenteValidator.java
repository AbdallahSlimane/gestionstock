package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.VenteDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VenteDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le champ code");
            errors.add("Veuillez renseigner le champ date de la vente");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCode())){
            errors.add("Veuillez renseigner le champ code");
        }

        if (dto.getDateVente() == null){
            errors.add("Veuillez renseigner le champ date de la vente");
        }

        return errors;

    }
}
