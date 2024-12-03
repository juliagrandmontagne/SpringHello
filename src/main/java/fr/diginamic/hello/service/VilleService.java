package fr.diginamic.hello.service;

import fr.diginamic.hello.Model.Ville;
import fr.diginamic.hello.Model.VilleRepository;
import fr.diginamic.hello.VilleValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VilleService{

    private final VilleRepository villeRepository;
    public Ville addVille(Ville ville) throws VilleValidationException {
        if (ville.getPopulation() < 10) {
            throw new VilleValidationException("La ville doit avoir au moins 10 habitants.");
        }
        if (ville.getNom().length() < 2) {
            throw new VilleValidationException("Le nom de la ville doit comporter au moins 2 lettres.");
        }
        if (ville.getDepartement().getCode().length() != 2) {
            throw new VilleValidationException("Le code département doit comporter exactement 2 caractères.");
        }
        // Vérification du nom unique dans le département
        if (villeRepository.existsByNomAndDepartementId(ville.getNom(), ville.getDepartement().getId())) {
            throw new VilleValidationException("Le nom de la ville doit être unique pour ce département.");
        }

        return villeRepository.save(ville);
    }

    // Mettre à jour une ville
    public Ville updateVille(int id, Ville updatedVille) throws VilleValidationException {
        if (updatedVille.getPopulation() < 10) {
            throw new VilleValidationException("La ville doit avoir au moins 10 habitants.");
        }
        if (updatedVille.getNom().length() < 2) {
            throw new VilleValidationException("Le nom de la ville doit comporter au moins 2 lettres.");
        }
        if (updatedVille.getDepartement().getCode().length() != 2) {
            throw new VilleValidationException("Le code département doit comporter exactement 2 caractères.");
        }
        // Vérification du nom unique dans le département
        if (villeRepository.existsByNomAndDepartementId(updatedVille.getNom(), updatedVille.getDepartement().getId())) {
            throw new VilleValidationException("Le nom de la ville doit être unique pour ce département.");
        }

        return villeRepository.save(updatedVille);
    }

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    public Page<Ville> getAllVilles(Pageable pageable) {
        return villeRepository.findAll(pageable);
    }
    public List<Ville> getTopNVillesByPopulation(Long departementId, int n) {
        Pageable pageable = PageRequest.of(0, n); // On veut les 'n' villes les plus peuplées
        return villeRepository.findByDepartementIdOrderByPopulationDesc(departementId, pageable);
    }

    // Recherche des villes par nom
    public Page<Ville> searchVillesByNom(String nom, Pageable pageable) throws VilleValidationException {
        Page<Ville> villes = villeRepository.findByNomStartingWith(nom, pageable);
        if (villes.isEmpty()) {
            throw new VilleValidationException("Aucune ville dont le nom commence par " + nom + " n'a été trouvée.");
        }
        return villes;
    }

    // Recherche des villes par population minimale
    public Page<Ville> searchVillesByPopulation(int minPopulation, Pageable pageable) throws VilleValidationException {
        Page<Ville> villes = villeRepository.findByPopulationGreaterThan(minPopulation, pageable);
        if (villes.isEmpty()) {
            throw new VilleValidationException("Aucune ville n'a une population supérieure à " + minPopulation);
        }
        return villes;
    }

    // Recherche des villes entre une population minimale et maximale
    public Page<Ville> searchVillesByPopulationRange(long departementId,int min, int max, Pageable pageable) throws VilleValidationException {
        Page<Ville> villes = villeRepository.findByPopulationBetween(min, max, pageable);
        if (villes.isEmpty()) {
            throw new VilleValidationException("Aucune ville n'a une population comprise entre " + min + " et " + max);
        }
        return villes;
    }


}
