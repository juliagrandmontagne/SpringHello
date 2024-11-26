package fr.diginamic.hello.service;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.DepartementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementDAO departementDao;

    public List<Departement> getAllDepartements() {
        return departementDao.findAll();
    }

    public Optional<Departement> getDepartementById(Long id) {
        return departementDao.findById(id);
    }

    public Optional<Departement> getDepartementByCode(String code) {
        return departementDao.findByCode(code);
    }

    public Departement addDepartement(Departement departement) {
        return departementDao.save(departement);
    }

    public Departement updateDepartement(Long id, Departement updatedDepartement) {
        return departementDao.findById(id)
                .map(departement -> {
                    departement.setCode(updatedDepartement.getCode());
                    departement.setNom(updatedDepartement.getNom());
                    return departementDao.save(departement);
                })
                .orElse(null);
    }

    public void deleteDepartement(Long id) {
        departementDao.deleteById(id);
    }
}
