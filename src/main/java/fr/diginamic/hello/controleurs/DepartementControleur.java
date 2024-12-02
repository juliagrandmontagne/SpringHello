package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.*;
import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
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

    // Lister tous les départements
    @GetMapping
    public List<DepartementDto> getAllDepartements() {
        return departementService.getAllDepartements()
                .stream()
                .map(DepartementMapper::toDto)
                .toList();
    }

    // Récupérer un département par ID
    @GetMapping("/{id}")
    public Optional<DepartementDto> getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id)
                .map(DepartementMapper::toDto);
    }

    // Ajouter un département
    @PostMapping
    public DepartementDto addDepartement(@RequestBody DepartementDto departementDto) {
        Departement departement = new Departement();
        // Vous devrez mapper DepartementDto vers Departement
        departement.setCode(departementDto.getCodeDepartement());
        departement.setNom(departementDto.getNomDepartement());
        //departement.setNombreHabitants(departementDto.getNombreHabitants());
        Departement savedDepartement = departementService.addDepartement(departement);
        return DepartementMapper.toDto(savedDepartement);
    }

    // Mettre à jour un département
    @PutMapping("/{id}")
    public DepartementDto updateDepartement(@PathVariable Long id, @RequestBody DepartementDto departementDto) {
        Departement departement = new Departement();
        // Mapper DepartementDto vers Departement
        departement.setCode(departementDto.getCodeDepartement());
        departement.setNom(departementDto.getNomDepartement());
        //departement.setNombreHabitants(departementDto.getNombreHabitants());
        Departement updatedDepartement = departementService.updateDepartement(id, departement);
        return DepartementMapper.toDto(updatedDepartement);
    }

    // Supprimer un département
    @DeleteMapping("/{id}")
    public void deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
    }

    // Lister les n plus grandes villes d’un département
    @GetMapping("/{departementId}/top/{n}")
    public List<VilleDto> getTopNVilles(@PathVariable Long departementId, @PathVariable int n) {
        return villeService.getTopNVillesByPopulation(departementId, n)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    // Lister les villes ayant une population entre un min et un max
    @GetMapping("/{departementId}/villes")
    public Page<VilleDto> getVillesByPopulationRange(
            @PathVariable Long departementId,
            @RequestParam int min,
            @RequestParam int max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)throws VilleValidationException {  // Ajout du "throws VilleValidationException"
        Pageable pageable = PageRequest.of(page, size);
        return villeService.searchVillesByPopulationRange(departementId, min, max, pageable)
                .map(VilleMapper::toDto);
    }
}


