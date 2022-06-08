package com.abdproject.gestionstock.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandefournisseur")

public class LigneCommandeFournisseur extends AbstractEntity{


    @ManyToOne
    @JoinColumn(name = "idarticle")
    private  Article article;

    @ManyToOne
    @JoinColumn(name = "commandeFournisseur")
    private CommandeFournisseur commandeFournisseur;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "identreprise")
    private Integer identreprise;

    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;
}
