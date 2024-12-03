package fr.diginamic.hello.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {

    // Recherche d'un département par son code
    Optional<Departement> findByCode(String code);
}
