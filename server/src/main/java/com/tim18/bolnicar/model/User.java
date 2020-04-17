package com.tim18.bolnicar.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    //error IDENTITY; https://stackoverflow.com/questions/916169/cannot-use-identity-column-key-generation-with-union-subclass-table-per-clas
    protected Integer id;

    @Column(unique = true, nullable = false)
    protected String emailAddress;

    @Column
    protected String password;

    @Column(nullable = false)
    protected String firstName;

    @Column(nullable = false)
    protected String lastName;

    @Column(nullable = false)
    protected String address;

    @Column(nullable = false)
    protected String city;

    @Column(nullable = false)
    protected String country;

    @Column(nullable = false)
    protected String contact;

    @Column(unique = true, nullable = false)
    protected String jmbg;

    @Column
    protected Boolean active;
}
