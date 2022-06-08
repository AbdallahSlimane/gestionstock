package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.RolesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RolesValidator {

    public static List<String> validate(RolesDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le Nom du role");
            errors.add("Veuillez sélectionner l'utilisation");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getRoleName())){
            errors.add("Veuillez renseigner le Nom du role");
        }

        if (dto.getUtilisateur() == null){
            errors.add("Veuillez sélectionner l'utilisation");
        }

        return errors;
    }
}
