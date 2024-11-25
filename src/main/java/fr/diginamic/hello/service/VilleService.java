package fr.diginamic.hello.service;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VilleService {

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
}
