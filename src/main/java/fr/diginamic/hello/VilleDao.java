package fr.diginamic.hello;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VilleDao extends JpaRepository<Ville, Integer> {
    Ville findByNom(String nom);

    // Rechercher toutes les villes par ID de département
    List<Ville> findByDepartementId(Long departementId);
}
