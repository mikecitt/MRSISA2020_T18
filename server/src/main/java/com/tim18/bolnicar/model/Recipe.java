package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Drug> drugs;

    @Column
    private Boolean sealed;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Nurse nurse;
}
