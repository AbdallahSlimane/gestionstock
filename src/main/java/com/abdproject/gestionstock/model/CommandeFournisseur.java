package com.abdproject.gestionstock.model;


import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandefournisseur")

public class CommandeFournisseur extends AbstractEntity{


    @Column(name = "code")
    private String code;

    @Column(name = "dateCommande")
    private Instant dateCommande;

    @Column(name = "etatcommande")
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "idFournisseur")
    private Fournisseur fournisseur;

    @Column(name = "identreprise")
    private Integer identreprise;

    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}
