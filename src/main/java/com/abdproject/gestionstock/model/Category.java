package com.abdproject.gestionstock.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category  extends AbstractEntity{


    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String descritpion;

    @Column(name = "identreprise")
    private Integer identreprise;

    @OneToMany(mappedBy = "category" )
    private List<Article> articles;

}
