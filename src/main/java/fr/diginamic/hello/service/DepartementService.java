package fr.diginamic.hello.service;

import fr.diginamic.hello.Model.Departement;
import fr.diginamic.hello.Model.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    public Optional<Departement> getDepartementById(Long id) {
        return departementRepository.findById(id);
    }

    public Optional<Departement> getDepartementByCode(String code) {
        return departementRepository.findByCode(code);
    }

    public Departement addDepartement(Departement departement) {
        return departementRepository.save(departement);
    }

    public Departement updateDepartement(Long id, Departement updatedDepartement) {
        return departementRepository.findById(id).map(departement -> {
            departement.setCode(updatedDepartement.getCode());
            departement.setNom(updatedDepartement.getNom());
            return departementRepository.save(departement);
        }).orElse(null);
    }

    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }
}
