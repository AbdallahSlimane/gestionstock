package com.abdproject.gestionstock.model;


import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mouvementstock")

public class MouvementStock extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private  Article article;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "datemvt")
    private Instant dateMvt;

    @Column(name = "identreprise")
    private Integer identreprise;

    @Column(name = "sourcemvt")
    private SourceMvtStk sourceMvt;

    @Column(name = "typemvt")
    private TypeMvtStk typeMvt;


}
