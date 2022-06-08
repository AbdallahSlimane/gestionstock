package com.abdproject.gestionstock.validator;

import com.abdproject.gestionstock.dto.LigneCommandeClientDto;
import com.abdproject.gestionstock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {

    public static List<String> validate(LigneCommandeFournisseurDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("Veuillez renseigner le champ Quantité");
            errors.add("Veuillez renseigner le champ Prix unitaire");
            errors.add("Veuillez sélectionner un Article ");
            return errors;
        }

        if (dto.getQuantite() == null){
            errors.add("Veuillez renseigner le champ Quantité");
        }

        if (dto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le champ Prix unitaire");
        }

        if (dto.getArticle() == null){
            errors.add("Veuillez sélectionner un Article");
        }

        if (dto.getCommandeFournisseur() == null){
            errors.add("Veuillez sélectionner une Commande");
        }

        return errors;
    }
}
