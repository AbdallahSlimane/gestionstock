package com.abdproject.gestionstock.model;


import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandeclient")
public class LigneCommandeClient extends AbstractEntity{


    @ManyToOne
    @JoinColumn(name = "idarticle")
    private  Article article;

    @ManyToOne
    @JoinColumn(name = "commandeClient")
    private CommandeClient commandeClient;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "identreprise")
    private Integer identreprise;


    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;

}
