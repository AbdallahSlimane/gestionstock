package com.abdproject.gestionstock.repository;

import com.abdproject.gestionstock.model.LigneCommandeClient;
import com.abdproject.gestionstock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {

    List<LigneCommandeClient> findAllByCommandeClientId(Integer idCommande);

    List<LigneCommandeClient> findAllByArticleId(Integer idCommande);


}
