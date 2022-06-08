package com.abdproject.gestionstock.model;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")

public class Roles extends AbstractEntity{

    @Column(name = "rolenom")
    private String roleName;

    @Column(name = "identreprise")
    private Integer identreprise;

    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;
}
