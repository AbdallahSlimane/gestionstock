package com.abdproject.gestionstock.services;

import com.abdproject.gestionstock.dto.CategoryDto;
import com.abdproject.gestionstock.dto.VenteDto;

import java.util.List;

public interface VenteService {

    VenteDto save(VenteDto dto);

    VenteDto findById(Integer id);

    VenteDto findByCode(String code);

    List<VenteDto> findAll();

    void delete(Integer id);
}
