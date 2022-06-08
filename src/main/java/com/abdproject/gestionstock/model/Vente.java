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
@Table(name = "vente")

public class Vente extends AbstractEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "datevente")
    private Instant dateVente;

    @Column(name = "identreprise")
    private Integer identreprise;

    @OneToMany(mappedBy = "vente")
    private List<LigneVente> ligneVentes;

    @Column(name = "commentaire")
    private  String commentaire;
}
