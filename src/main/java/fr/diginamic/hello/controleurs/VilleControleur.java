package fr.diginamic.hello.controleurs;
import fr.diginamic.hello.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ville")
public class VilleControleur {

    private List<Ville> villes = new ArrayList<>();

    public VilleControleur() {
        villes.add(new Ville("Paris", 2148000));    // Pas besoin de "String" ici
        villes.add(new Ville("Lyon", 513275));
        villes.add(new Ville("Marseille", 861635));
    }
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }
}
