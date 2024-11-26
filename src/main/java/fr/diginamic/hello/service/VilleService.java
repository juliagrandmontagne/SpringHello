package fr.diginamic.hello.service;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VilleService{

@Autowired
private VilleDao villeDao;

public List<Ville> getAllVilles() {
    return villeDao.findAll();
}

public Ville getVilleById(int id) {
    return villeDao.findById(id).orElse(null);
}

public Ville getVilleByNom(String nom) {
    return villeDao.findByNom(nom);
}

public List<Ville> addVille(Ville ville) {
    villeDao.save(ville);
    return villeDao.findAll();
}

public List<Ville> updateVille(int id, Ville updatedVille) {
    Ville ville = villeDao.findById(id).orElse(null);
    if (ville != null) {
        ville.setNom(updatedVille.getNom());
        ville.setPopulation(updatedVille.getPopulation());
        ville.setDepartement(updatedVille.getDepartement());
        villeDao.save(ville);
    }
    return villeDao.findAll();
}

public List<Ville> deleteVille(int id) {
    villeDao.deleteById(id);
    return villeDao.findAll();
}

// Récupérer les n plus grandes villes par population pour un département donné
public List<Ville> getTopNVillesByPopulation(Long departementId, int n) {
    return villeDao.findByDepartementId(departementId).stream()
            .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
            .limit(n)
            .collect(Collectors.toList());
}

// Récupérer les villes ayant une population entre min et max pour un département donné
public List<Ville> getVillesByPopulationRange(Long departementId, int min, int max) {
    return villeDao.findByDepartementId(departementId).stream()
            .filter(ville -> ville.getPopulation() >= min && ville.getPopulation() <= max)
            .collect(Collectors.toList());
}
}
