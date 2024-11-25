package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ville")
public class VilleControleur {

    @Autowired
    private VilleService villeService;

    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.getAllVilles();
    }

    @GetMapping("/{id}")
    public Ville getVilleById(@PathVariable int id) {
        return villeService.getVilleById(id);
    }

    @GetMapping("/nom/{nom}")
    public Ville getVilleByNom(@PathVariable String nom) {
        return villeService.getVilleByNom(nom);
    }

    @PostMapping
    public List<Ville> addVille(@RequestBody Ville ville) {
        return villeService.addVille(ville);
    }

    @PutMapping("/{id}")
    public List<Ville> updateVille(@PathVariable int id, @RequestBody Ville ville) {
        return villeService.updateVille(id, ville);
    }

    @DeleteMapping("/{id}")
    public List<Ville> deleteVille(@PathVariable int id) {
        return villeService.deleteVille(id);
    }
}
