package com.abdproject.gestionstock.repository;

import com.abdproject.gestionstock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findUtilisateurByMail(String mail);

}
