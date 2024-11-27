package fr.diginamic.hello;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    // Recherche par nom (commence par une chaîne)
    List<Ville> findByNomStartingWith(String nom);

    // Population supérieure à un seuil
    List<Ville> findByPopulationGreaterThan(int min);

    // Population entre deux seuils
    List<Ville> findByPopulationBetween(int min, int max);

    // Population d'un département supérieure à un seuil
    List<Ville> findByDepartementIdAndPopulationGreaterThan(Long departementId, int min);

    // Population d'un département entre deux seuils
    List<Ville> findByDepartementIdAndPopulationBetween(Long departementId, int min, int max);

    // Les n villes les plus peuplées d’un département donné
    List<Ville> findByDepartementIdOrderByPopulationDesc(Long departementId, Pageable pageable);
}
