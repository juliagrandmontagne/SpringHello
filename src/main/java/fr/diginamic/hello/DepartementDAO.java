package fr.diginamic.hello;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartementDAO extends JpaRepository<Departement, Long> {

    Optional<Departement> findByCode(String code); // Rechercher par code
}

