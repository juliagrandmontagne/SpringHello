package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleMapper;
import fr.diginamic.hello.VilleRepository;
import fr.diginamic.hello.VilleValidationException;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ville")
public class VilleControleur {

    private final VilleService villeService;

    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    // Pagination des villes
    @GetMapping("/pagination")
    public Page<VilleDto> getPaginatedVilles(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeService.getAllVilles(pageable).map(VilleMapper::toDto);
    }

    // Recherche des villes par nom
    @GetMapping("/search-by-nom")
    public Page<VilleDto> searchVillesByNom(@RequestParam String nom,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) throws VilleValidationException {  // Ajout du "throws VilleValidationException"
        Pageable pageable = PageRequest.of(page, size);
        return villeService.searchVillesByNom(nom, pageable).map(VilleMapper::toDto);
    }

    // Recherche des villes d'un département ayant une population entre min et max
    @GetMapping("/{departementId}/villes")
    public Page<VilleDto> getVillesByPopulationRange(
            @PathVariable Long departementId,
            @RequestParam int min,
            @RequestParam int max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws VilleValidationException {  // Ajout du "throws VilleValidationException"
        Pageable pageable = PageRequest.of(page, size);
        return villeService.searchVillesByPopulationRange(departementId,min, max, pageable)  // Appel de la méthode existante
                .map(VilleMapper::toDto);  // Utilisation de VilleMapper pour transformer les entités en DTO
    }

    // Recherche des villes par population minimale
    @GetMapping("/search-by-population")
    public Page<VilleDto> searchVillesByPopulation(@RequestParam int minPopulation,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) throws VilleValidationException {
        Pageable pageable = PageRequest.of(page, size);
        return villeService.searchVillesByPopulation(minPopulation, pageable).map(VilleMapper::toDto);
    }
}


