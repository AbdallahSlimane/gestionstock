package com.abdproject.gestionstock.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity {


    @Column(name= "codeArticle", length = 16)
    private String codeArticle;

    @Column(name = "description", length = 200)
    private String despcription;

    @Column(name = "PrixUnitaireHt")
    private BigDecimal prixUnitaireHt;

    @Column(name = "tauxTva")
    private BigDecimal tauxTva;

    @Column(name = "PrixUnitaireTTC")
    private BigDecimal prixUnitaireTTC;

    @Column(name = "photo")
    private String photo;

    @Column(name = "identreprise")
    private Integer identreprise;

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<LigneVente> lignesVentes;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeClient> ligneCommandeClients;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    @OneToMany(mappedBy = "article")
    private List<MouvementStock> mouvementStocks;



}
