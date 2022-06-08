package com.abdproject.gestionstock.services;


import com.abdproject.gestionstock.dto.ChangerMdpUtilisateurDto;
import com.abdproject.gestionstock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    UtilisateurDto findByMail(String mail);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    UtilisateurDto changerMotDePasse(ChangerMdpUtilisateurDto dto);
}
