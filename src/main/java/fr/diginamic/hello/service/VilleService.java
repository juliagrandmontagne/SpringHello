package fr.diginamic.hello.service;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Page<Ville> searchVillesByNom(String nom, Pageable pageable) {
        return villeRepository.findByNomStartingWith(nom, pageable);
    }

    public Page<Ville> getVillesByPopulationRange(Long departementId, int min, int max, Pageable pageable) {
        return villeRepository.findByDepartementIdAndPopulationBetween(departementId, min, max, pageable);
    }

    public Page<Ville> searchVillesByPopulation(int minPopulation, Pageable pageable) {
        return villeRepository.findByPopulationGreaterThan(minPopulation, pageable);
    }
}
