package fr.diginamic.hello;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    // Recherche par nom (commence par une chaîne) avec pagination
    Page<Ville> findByNomStartingWith(String nom, Pageable pageable);

    // Population supérieure à un seuil avec pagination
    Page<Ville> findByPopulationGreaterThan(int min, Pageable pageable);

    // Population entre deux seuils avec pagination
    Page<Ville> findByPopulationBetween(int min, int max, Pageable pageable);

    // Population d'un département supérieure à un seuil avec pagination
    Page<Ville> findByDepartementIdAndPopulationGreaterThan(Long departementId, int min, Pageable pageable);

    // Population d'un département entre deux seuils avec pagination
    Page<Ville> findByDepartementIdAndPopulationBetween(Long departementId, int min, int max, Pageable pageable);

    // Les n villes les plus peuplées d’un département donné
    List<Ville> findByDepartementIdOrderByPopulationDesc(Long departementId, Pageable pageable);

    boolean existsByNomAndDepartementId(String nom, Long departementId);
}
