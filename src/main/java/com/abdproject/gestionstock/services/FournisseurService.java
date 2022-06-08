package com.abdproject.gestionstock.services;

import com.abdproject.gestionstock.dto.EntrepriseDto;
import com.abdproject.gestionstock.dto.FournisseurDto;

import java.util.List;

public interface FournisseurService {

    FournisseurDto save(FournisseurDto dto);

    FournisseurDto findById(Integer id);


    List<FournisseurDto> findAll();

    void delete(Integer id);
}
