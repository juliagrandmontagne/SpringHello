package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleRepository;
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

    @GetMapping("/pagination")
    public Page<Ville> getPaginatedVilles(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeService.getAllVilles(pageable);
    }

    @GetMapping("/search-by-nom")
    public Page<Ville> searchVillesByNom(@RequestParam String nom,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeService.searchVillesByNom(nom, pageable);
    }

    @GetMapping("/{departementId}/villes")
    public Page<Ville> getVillesByPopulationRange(
            @PathVariable Long departementId,
            @RequestParam int min,
            @RequestParam int max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Crée un objet Pageable avec les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Appelle la méthode avec tous les paramètres requis
        return villeService.getVillesByPopulationRange(departementId, min, max, pageable);
    }

    @GetMapping("/search-by-population")
    public Page<Ville> searchVillesByPopulation(@RequestParam int minPopulation,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeService.searchVillesByPopulation(minPopulation, pageable);
    }
}

