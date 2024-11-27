package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleRepository;
import fr.diginamic.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ville")
public class VilleControleur {

    @Autowired
    private VilleRepository villeRepository;

    @GetMapping
    public List<Ville> getAllVilles(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable).getContent();
    }

    @GetMapping("/nom/{nom}")
    public List<Ville> getVilleByNom(@PathVariable String nom) {
        return villeRepository.findByNomStartingWith(nom);
    }

    @GetMapping("/population/min/{min}")
    public List<Ville> getVilleByMinPopulation(@PathVariable int min) {
        return villeRepository.findByPopulationGreaterThan(min);
    }

    @GetMapping("/population/range")
    public List<Ville> getVillesByPopulationRange(@RequestParam int min, @RequestParam int max) {
        return villeRepository.findByPopulationBetween(min, max);
    }

    @GetMapping("/departement/{departementId}/population/min/{min}")
    public List<Ville> getVillesByDepartementAndMinPopulation(@PathVariable Long departementId,
                                                              @PathVariable int min) {
        return villeRepository.findByDepartementIdAndPopulationGreaterThan(departementId, min);
    }

    @GetMapping("/departement/{departementId}/top")
    public List<Ville> getTopNVilles(@PathVariable Long departementId, @RequestParam int n) {
        Pageable pageable = PageRequest.of(0, n);
        return villeRepository.findByDepartementIdOrderByPopulationDesc(departementId, pageable);
    }
}
