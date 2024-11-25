package fr.diginamic.hello;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    private List<fr.diginamic.hello.Ville> villes;

    // Getters, Setters et constructeur
}

