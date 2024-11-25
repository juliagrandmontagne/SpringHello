package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ville")
public class VilleControleur {

    private List<Ville> villes = new ArrayList<>();

    public VilleControleur() {
        villes.add(new Ville(1,"Paris", 2148000));    // Pas besoin de "String" ici
        villes.add(new Ville(2,"Lyon", 513275));
        villes.add(new Ville(3,"Marseille", 861635));
    }
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Ville ville = villes.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);

        if (ville == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ville);
    }

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {
        // Vérification si une ville avec le même nom existe déjà
        boolean exists = villes.stream()
                .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La ville existe déjà.");
        }

        if (ville.getNom() == null || ville.getNom().length() < 2) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Le nom de la ville doit contenir au moins 2 caractères.");
    }
         if (ville.getNbHabitants() < 1) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Le nombre d'habitants doit être supérieur ou égal à 1.");
    }
             villes.add(ville);
    return ResponseEntity.status(HttpStatus.OK)
            .body("Ville insérée avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville ville) {
        // Recherche de la ville existante
        Ville existingVille = villes.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);

        if (existingVille == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ville avec l'id " + id + " non trouvée.");
        }

        // Validation des données
        if (ville.getNom() == null || ville.getNom().length() < 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Le nom de la ville doit contenir au moins 2 caractères.");
        }
        if (ville.getNbHabitants() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Le nombre d'habitants doit être supérieur ou égal à 1.");
        }

        // Mise à jour des données
        existingVille.setNom(ville.getNom());
        existingVille.setNbHabitants(ville.getNbHabitants());

        return ResponseEntity.status(HttpStatus.OK)
                .body("Ville mise à jour avec succès.");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        boolean removed = villes.removeIf(v -> v.getId() == id);

        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ville avec l'id " + id + " non trouvée.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Ville supprimée avec succès.");
    }
}
