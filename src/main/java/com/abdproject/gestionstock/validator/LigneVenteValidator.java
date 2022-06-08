package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.LigneVenteDto;

import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {

    public static List<String> validate(LigneVenteDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le champ Quantité");
            errors.add("Veuillez renseigner le champ Prix unitaire");
            errors.add("Veuillez selectionner une Vente");
            return errors;
        }

        if (dto.getQuantite() == null){
            errors.add("Veuillez renseigner le champ Quantité");
        }

        if (dto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le champ Prix unitaire");
        }

        if (dto.getVente() == null){
            errors.add("Veuillez selectionner une Vente");
        }
        return errors;

    }
}
