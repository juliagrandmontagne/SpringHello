package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.service.DepartementService;
import fr.diginamic.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("departement")
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    @Autowired
    private VilleService villeService;

    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.getAllDepartements();
    }

    @GetMapping("/{id}")
    public Optional<Departement> getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }

    @PostMapping
    public Departement addDepartement(@RequestBody Departement departement) {
        return departementService.addDepartement(departement);
    }

    @PutMapping("/{id}")
    public Departement updateDepartement(@PathVariable Long id, @RequestBody Departement departement) {
        return departementService.updateDepartement(id, departement);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
    }

    // Lister les n plus grandes villes d’un département
    @GetMapping("/{departementId}/top/{n}")
    public List<Ville> getTopNVilles(@PathVariable Long departementId, @PathVariable int n) {
        return villeService.getTopNVillesByPopulation(departementId, n);
    }

    // Lister les villes ayant une population entre un min et un max et appartenant à un département donné
    @GetMapping("/{departementId}/villes")
    public Page<Ville> getVillesByPopulationRange(
            @PathVariable Long departementId,
            @RequestParam int min,
            @RequestParam int max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Créez un objet Pageable pour gérer la pagination
        Pageable pageable = PageRequest.of(page, size);
        return villeService.getVillesByPopulationRange(departementId, min, max, pageable);
    }
}

