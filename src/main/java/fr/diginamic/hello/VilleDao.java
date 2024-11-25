package fr.diginamic.hello;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VilleDao extends JpaRepository<Ville, Integer> {
    Ville findByNom(String nom); // Requête personnalisée
}
