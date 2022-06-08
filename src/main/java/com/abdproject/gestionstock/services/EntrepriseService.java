package com.abdproject.gestionstock.services;

import com.abdproject.gestionstock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer id);


    List<EntrepriseDto> findAll();

    void delete(Integer id);
}
