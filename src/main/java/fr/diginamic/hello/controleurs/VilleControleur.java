package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Model.Ville;
import fr.diginamic.hello.Model.VilleMapper;
import fr.diginamic.hello.VilleValidationException;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.service.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/export-csv")
    public void exportToCsv(@RequestParam int minPopulation, HttpServletResponse response) throws Exception {
        // Définir les en-têtes HTTP pour le fichier CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"villes.csv\"");

        // Passer un Pageable pour récupérer toutes les villes
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE); // Pas de limite de pagination
        List<Ville> villes = villeService.searchVillesByPopulation(minPopulation, pageable).getContent();

        // Écriture du fichier CSV
        try (PrintWriter writer = response.getWriter()) {
            writer.println("Nom de la ville,Nombre d'habitants,Code département,Nom du département");

            for (Ville ville : villes) {
                // Récupérer le nom du département via l'API externe
                String apiUrl = "https://geo.api.gouv.fr/departements/" + ville.getDepartement().getCode() + "?fields=nom,code,codeRegion";
                String nomDepartement = fetchNomDepartement(apiUrl);

                // Écrire une ligne pour chaque ville
                writer.printf("%s,%d,%s,%s%n",
                        ville.getNom(),
                        ville.getPopulation(),
                        ville.getDepartement().getCode(),
                        nomDepartement
                );
            }
        }
    }

    // Méthode utilitaire pour récupérer le nom du département depuis l'API externe
    private String fetchNomDepartement(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Effectuer une requête GET à l'API externe
            Map<String, String> response = restTemplate.getForObject(apiUrl, Map.class);
            return response != null ? response.get("nom") : "Inconnu";
        } catch (Exception e) {
            return "Inconnu"; // Retourner "Inconnu" si une erreur survient
        }
    }

//    }

}


