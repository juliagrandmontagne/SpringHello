package fr.diginamic.hello;

import fr.diginamic.hello.Departement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Identifiant auto-incrémenté

    private String nom; // Nom de la ville

    private int population; // Population de la ville

    @ManyToOne
    @JoinColumn(name = "departement_id") // Clé étrangère pour lier au département
    private Departement departement;

    // Constructeur sans paramètres requis par JPA
    public Ville() {}

    // Constructeur avec paramètres
    public Ville(String nom, int population,Departement departement) {
        this.nom = nom;
        this.population = population;
        this.departement = departement;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ville{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", population=").append(population);
        sb.append(", departement=").append(departement);
        sb.append('}');
        return sb.toString();
    }
}
